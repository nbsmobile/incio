 <img src="images/logo.png" alt="Incio logo"/>

## <a name="introduction"></a> Introduction

The goal of this Template is to be our starting point for new projects, following the best development practices. It's our interpretation and adaptation of years in mobile development that we have implemented in our internal codebases for all kind of Mobile Projects.  

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

## <a name="architecture"></a> Architecture
<img width="666" alt="Screen Shot 2023-02-03 at 9 13 05 PM" src="https://user-images.githubusercontent.com/55146646/216612133-81c9544a-96d5-4d2d-9141-c10c02120b3e.png">


## <a name="features"></a> Features

This template includes:

* Expect actual implementation
  - Cryptography SHA 256

* Networking : 
  - HTTP GET
  - HTTP POST
  - HTTP POST Multipart/Form-Data [Viky] 
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

- Follow the [KMM Guide by Jetbrains](https://kotlinlang.org/docs/kmm-overview.html) for getting started building a project with KMM.
- Install Kotlin Multiplatform Mobile plugin in Android Studio
- Clone or Download the repo
- Rebuild Project
- To run in iOS, Open Xcode, select `.xcworkspace`, then `pod install` inside `iosApp` folder to install shared module and ios dependencies

<!-- **Development Keys**: The `apiKey` in [`utils/Constants.kt`](https://code.nbs.dev/nbs-mobile/kmm-movie-db/-/blob/main/core/src/commonMain/kotlin/com/uwaisalqadri/moviecatalogue/utils/Constants.kt) are generated from [TMDB](https://www.themoviedb.org/), generate your own in [themoviedb.org/settings/api](https://www.themoviedb.org/settings/api). -->

## <a name="libraries"></a> Libraries

<img width="659" alt="Screen Shot 2023-02-03 at 7 08 19 PM" src="https://user-images.githubusercontent.com/55146646/216588897-952285ad-6677-4812-9002-dc5b9ccc661c.png">

## <a name="domain-to-presentation"></a> Domain to Presentation
In Android, Because both `shared` and `androidApp` written in Kotlin, we can simply collect flow :
```
fun getRocketLaunches() = viewModelScope.launch {
   _rocketLaunchResults.value = Resource.loading()
   proceed(_rocketLaunchResults) {
      rocketLaunchUseCase.getRocketLaunches()
   }
}

```

But in iOS, we have to deal with swift, here i'm using `createPublisher()` from `KMPNativeCoroutines` to collect flow as Publisher in `Combine` :

```
func getRocketLaunches() {
   rocketLaunch = .loading
   viewStatePublisher(
     for: rocketLaunchUseCase.getRocketLaunchesNative(),
     in: &cancellables
   ) { self.rocketLaunch = $0 }
}

```

both `proceed()` and `viewStatePublisher(for: , in:)` are the same logic under the hood, to handle general error, reactively retrying the function, etc.
 
learn more: https://github.com/rickclephas/KMP-NativeCoroutines

## <a name="expect-actual"></a> Expect and Actual
in KMM, there is a negative case when there's no support to share code for some feature in both ios and android, and it's expensive to write separately in each module

so the solution is ✨`expect` and `actual`✨, we can write `expect` inside `commonMain` and write "actual" implementation with `actual` inside `androidMain` and `iosMain`
and then each module will use `expect`

example:

[**`commonMain/Platform.kt`**](oke)
```
expect fun getRequestHash(): String
```

[**`commonMain/Platform.kt`**](ppp)

```
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

[**`iosMain/Platform.kt`**](oke)

```
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
