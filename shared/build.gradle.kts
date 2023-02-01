@file:Suppress("UnstableApiUsage")

import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING

plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    kotlin("plugin.serialization") version "1.7.20"
    id("com.android.library")
    id("com.google.devtools.ksp") version "1.7.20-1.0.7"
    id("com.squareup.sqldelight")
    id("koin")
    id("com.rickclephas.kmp.nativecoroutines") version "0.13.1"
    id("dev.icerock.moko.kswift") version "0.6.0"
    id("com.codingfeline.buildkonfig") version "+"
}

// cocoapods version
version = "1.0"

val koinVersion = "3.2.0"
val ktorVersion = "2.2.1"
val reaktiveVersion = "1.2.2"
val sqlDelightVersion = "1.5.4"
val statelyVersion = "1.2.2"

buildkonfig {
    packageName = "com.nbs.kmm.sample"
    objectName = "NbsKmmSharedConfig"
    exposeObjectWithName = "NbsKmmSharedPublicConfig"

    // default config is required
    defaultConfigs {
        buildConfigField(STRING, "BASE_URL", "story-api.dicoding.dev/v1")
    }
}

kotlin {
    android()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "NBS KMM Mobile Shared Modulee"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "14.0"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "Shared"
            isStatic = true
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
                implementation("io.insert-koin:koin-core:$koinVersion")
                implementation("com.squareup.sqldelight:runtime:$sqlDelightVersion")

                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-auth:$ktorVersion")
                implementation("io.ktor:ktor-client-cio:$ktorVersion")
                implementation("io.ktor:ktor-client-logging:$ktorVersion")
                implementation("io.ktor:ktor-client-serialization:$ktorVersion")
                implementation("io.ktor:ktor-client-encoding:$ktorVersion")
                implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")

                implementation("com.russhwolf:multiplatform-settings-no-arg:1.0.0-RC")
                implementation("co.touchlab:stately-isolate:$statelyVersion")

                api("co.touchlab:kermit:1.1.3")
                implementation(kotlin("stdlib-common"))
                implementation("co.touchlab:stately-common:$statelyVersion")
                implementation("com.badoo.reaktive:reaktive:$reaktiveVersion")
                implementation("com.badoo.reaktive:reaktive-annotations:$reaktiveVersion")
                implementation("com.badoo.reaktive:coroutines-interop:$reaktiveVersion")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation("io.mockative:mockative:1.2.6")
            }
        }

        val androidMain by getting {
            dependencies {
                implementation("com.squareup.sqldelight:android-driver:$sqlDelightVersion")
                implementation("io.ktor:ktor-client-android:$ktorVersion")
                implementation("com.squareup.okhttp3:okhttp:4.10.0")
                implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")
                implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
                implementation("com.github.chuckerteam.chucker:library:3.5.2")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13.2")
            }
        }

        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)

            dependencies {
                implementation("com.squareup.sqldelight:native-driver:$sqlDelightVersion")
                implementation("io.ktor:ktor-client-ios:$ktorVersion")
            }
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

// add support for kotlin extension function and sealed class to enum swift
kswift {
    install(dev.icerock.moko.kswift.plugin.feature.SealedToSwiftEnumFeature)
    install(dev.icerock.moko.kswift.plugin.feature.PlatformExtensionFunctionsFeature)
}

android {
    compileSdk = 33
    namespace = "com.nbs.kmm.sample"
    defaultConfig {
        minSdk = 24
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        testInstrumentationRunnerArguments["clearPackageData"] = "true"

        testOptions {
            execution = "ANDROIDX_TEST_ORCHESTRATOR"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    dependencies {
        androidTestImplementation("androidx.test:runner:1.5.2")
        androidTestUtil("androidx.test:orchestrator:1.4.2")
    }
}

dependencies {
    configurations
        .filter { it.name.startsWith("ksp") && it.name.contains("Test") }
        .forEach {
            add(it.name, "io.mockative:mockative-processor:1.2.6")
        }
}

sqldelight {
    database("AppDatabase") {
        packageName = "com.nbs.kmm.sample.cache"
        sourceFolders = listOf("sqldelight")
    }
}