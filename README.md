 <img src="images/logo.png" alt="Incio logo"/>

## <a name="introduction"></a> Introduction

The goal of this Template is to be our starting point for new projects, following the best
development practices. It's our interpretation and adaptation of years in mobile development that we
have implemented in our internal codebases for all kind of Mobile Projects.

**Module**

* **`shared`**: data and domain layer
* **`iosApp`**: ios presentation layer
* **`androidApp`**: android presentation layer

## Table of Contents

- [Introduction](#introduction)
- [Architecture](#architecture)
- [Features](#features)
- [Installation](#installation)
- [Libraries](#libraries)
- [Domain to Presentation](#domain-to-presentation)
- [Expect and Actual](#expect-actual)
- [Project Structure](#project-structure)
- [Build Config](#build-config)

## <a name="architecture"></a> Architecture
<img width="669" alt="Screen Shot 2023-02-04 at 12 19 59 PM" src="https://user-images.githubusercontent.com/55146646/216748266-75110db9-2bec-41aa-9e21-6cb0f477a961.png">

## <a name="features"></a> Features

This template includes:

* Expect actual implementation
    - Cryptography SHA 256

* Networking :
    - HTTP GET
    - HTTP POST
    - HTTP POST Multipart/Form-Data
    - Authenticator
    - HTTP Error Handler

* Database
    - Create
    - Read
    - Delete

* Preference
    - Read
    - Update

## <a name="installation"></a> Installation

- Follow the [KMM Guide by Jetbrains](https://kotlinlang.org/docs/kmm-overview.html) for getting
  started building a project with KMM.
- Install Kotlin Multiplatform Mobile plugin in Android Studio
- Clone or Download the repo
- Rebuild Project
- To run in iOS, Open Xcode, select `.xcworkspace`, then `pod install` inside `iosApp` folder to
  install shared module and ios dependencies

<!-- **Development Keys**: The `apiKey` in [`utils/Constants.kt`](https://code.nbs.dev/nbs-mobile/kmm-movie-db/-/blob/main/core/src/commonMain/kotlin/com/uwaisalqadri/moviecatalogue/utils/Constants.kt) are generated from [TMDB](https://www.themoviedb.org/), generate your own in [themoviedb.org/settings/api](https://www.themoviedb.org/settings/api). -->

## <a name="libraries"></a> Libraries

<img width="659" alt="Screen Shot 2023-02-03 at 7 08 19 PM" src="https://user-images.githubusercontent.com/55146646/216588897-952285ad-6677-4812-9002-dc5b9ccc661c.png">

## <a name="domain-to-presentation"></a> Domain to Presentation

In Android, Because both `shared` and `androidApp` written in Kotlin, we can simply collect flow :

```kotlin
fun getRocketLaunches() = viewModelScope.launch {
   _rocketLaunchResults.value = Resource.loading()
   proceed(_rocketLaunchResults) {
      rocketLaunchUseCase.getRocketLaunches()
   }
}

```

But in iOS, we have to deal with swift, here i'm using `createPublisher()`
from `KMPNativeCoroutines` to collect flow as Publisher in `Combine` :

```swift
func getRocketLaunches() {
   rocketLaunch = .loading
   viewStatePublisher(
     for: rocketLaunchUseCase.getRocketLaunchesNative(),
     in: &cancellables
   ) { self.rocketLaunch = $0 }
}

```

both `proceed()` and `viewStatePublisher(for: , in:)` are the same logic under the hood, to handle
general error, reactively retrying the function, etc.

learn more: https://github.com/rickclephas/KMP-NativeCoroutines

## <a name="expect-actual"></a> Expect and Actual

in KMM, there is a negative case when there's no support to share code for some feature in both ios
and android, and it's expensive to write separately in each module

so the solution is ✨`expect` and `actual`✨, we can write `expect` inside `commonMain` and write "
actual" implementation with `actual` inside `androidMain` and `iosMain`
and then each module will use `expect`

example:

[**`commonMain/Platform.kt`**](https://github.com/nbsmobile/incio/blob/master/shared/src/commonMain/kotlin/com/nbs/kmm/sample/Platform.kt)

```kotlin
expect fun getRequestHash(): String
```

[**`androidMain/Platform.kt`**](https://github.com/nbsmobile/incio/blob/master/shared/src/androidMain/kotlin/com/nbs/kmm/sample/Platform.kt)

```kotlin
actual fun getRequestHash(): String {
  val key: String = "NBS KMM Sample"
  val timestamp = (System.currentTimeMillis() / 1000).toString()
  val algorithm: String = "HmacSHA256"
  val charset: Charset = Charset.forName("UTF-8")

  val sha256Hmac: Mac = Mac.getInstance(algorithm)
  val secretKeySpec = SecretKeySpec(key.toByteArray(charset), algorithm)
  sha256Hmac.init(secretKeySpec)
  val hash: String = bytesToHex(sha256Hmac.doFinal(timestamp.toByteArray(charset))).orEmpty()
  logging { "HASH ANDROID $hash" }
  return hash
}


```

[**`iosMain/Platform.kt`**](https://github.com/nbsmobile/incio/blob/master/shared/src/iosMain/kotlin/com/nbs/kmm/sample/Platform.kt)

```kotlin
actual fun getRequestHash(): String {
  val key = "NBS KMM Sample"
  val timestamp = NSDate().timeIntervalSince1970.toLong().toString()
  val hash = (timestamp as NSString).sha256Hmac(key = key)
  logging { "HASH IOS $hash" }
  return hash
}

@OptIn(ExperimentalUnsignedTypes::class)
fun NSString.sha256Hmac(algorithm: CCHmacAlgorithm = kCCHmacAlgSHA256, key: String): String {
  val string = this.cStringUsingEncoding(encoding = NSUTF8StringEncoding)
  val stringLength = this.lengthOfBytesUsingEncoding(NSUTF8StringEncoding)
  val digestLength = CC_SHA256_DIGEST_LENGTH
  var result = UByteArray(size = digestLength)
  val keyString = (key as NSString).cStringUsingEncoding(encoding = NSUTF8StringEncoding)
  val keyLength = key.lengthOfBytesUsingEncoding(NSUTF8StringEncoding)

  CCHmac(algorithm, keyString, keyLength, string, stringLength, result.refTo(0))

  return stringFromResult(result, digestLength)
}
```

yes, we can use `Foundation`, `CoreCrypto`, `CoreFoundation` same as what we use in Xcode

## <a name="project-structure"></a> Project Structure

**`shared`**:

* `base`
* `cache`
* `data`
    - `sample`
        - `model`
            - `response`
            - `request`
        - `remote`
            - `SampleApi`
            - `SampleApiClient`
* `di`
    - `ios`
    - `feature`
* `domain`
    - `sample`
        - `model`
        - `mapper`
        - `SampleInteractor`
        - `SampleUseCase`
* `utils`

- `enum`
- `eventbus`
- `ext`

**`androidApp`**:

- `base`
- `di`
- `sample`
- `theme`
- `utils`

**`iosApp`**:

- `Dependency`
- `App`
- `Main`
- `Resources`
- `ReusableView`
- `Extensions`
- `Utils`
- `Features`
    - `Sample`
        - `Navigator`
        - `Views`
        - `ViewModel`

## <a name="build_config"></a> Build Config

You can setup Build Config for multiple Environment, Just add your build configuration at
build.gradle on `shared` in the `buildkonfig` section like this:

```kotlin
buildkonfig {
    packageName = "com.nbs.kmm.sample"
    objectName = "NbsKmmSharedConfig"
    exposeObjectWithName = "NbsKmmSharedPublicConfig"

    // default config is required
    defaultConfigs {
        buildConfigField(STRING, "BASE_URL", "story-api.dicoding.dev")
        buildConfigField(STRING, "BASE_URL_SPACEX", "api.spacexdata.com")
    }

    // config for staging
    defaultConfigs("staging") {
        buildConfigField(STRING, "BASE_URL", "story-api.dicoding.dev")
        buildConfigField(STRING, "BASE_URL_SPACEX", "api.spacexdata.com")
    }

    // config for release
    defaultConfigs("release") {
        buildConfigField(STRING, "BASE_URL", "story-api.dicoding.dev")
        buildConfigField(STRING, "BASE_URL_SPACEX", "api.spacexdata.com")
    }
}
```

And for changing the Environment just set it on `gradle.properties`
with `buildkonfig.flavor` and assign the value with the Environment name that you want to use, for
default config just let the `buildkonfig.flavor` value to be empty
