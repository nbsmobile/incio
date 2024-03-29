@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "com.nbs.kmm.sample.android"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.nbs.kmm.sample.android"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
        viewBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.4"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("debug") {
            isDebuggable = true
            isMinifyEnabled = false
        }

        getByName("release") {
            isMinifyEnabled = false
        }
    }
    flavorDimensions.add("env")
    productFlavors {
        create("dev") {
            dimension = "env"
            applicationIdSuffix = ".dev"
//            signingConfig = signingConfigs.getByName("debug")
        }
        create("staging") {
            dimension = "env"
            applicationIdSuffix = ".staging"
//            signingConfig = signingConfigs.getByName("debug")
        }
        create("production") {
            dimension = "env"
            applicationIdSuffix = ""
//            signingConfig = signingConfigs.getByName("release")
        }
    }
}

dependencies {
    implementation(project(":shared"))
    implementation("androidx.multidex:multidex:2.0.1")

    // Koin
    implementation("io.insert-koin:koin-android:3.2.0")
    implementation("io.insert-koin:koin-androidx-workmanager:3.1.2")
    implementation("io.insert-koin:koin-android-ext:3.0.1")
    implementation("io.insert-koin:koin-androidx-compose:3.2.0")
    implementation("androidx.work:work-runtime:2.7.1")

    // Compose
    implementation("androidx.activity:activity-compose:1.6.1"){
        exclude("org.jetbrains.kotlin", "kotlin-stdlib")
    }
    implementation("androidx.compose.ui:ui:1.5.4")
    implementation("androidx.compose.material:material:1.5.4")
    implementation("androidx.compose.ui:ui-tooling:1.5.4")
    implementation("androidx.compose.runtime:runtime-livedata:1.5.4")

    //timber
    implementation("com.jakewharton.timber:timber:4.7.1")

    //rxjava
    implementation("io.reactivex.rxjava2:rxjava:2.2.21")
    implementation("io.reactivex.rxjava2:rxandroid:2.1.1")

    //gson
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    //ImageViewer
    implementation("com.github.stfalcon-studio:StfalconImageViewer:v1.0.1")

    //Camera
    implementation("io.fotoapparat:fotoapparat:2.7.0")

    //ImagePicker
    implementation("com.github.dhaval2404:imagepicker:2.1")

    // Crop Image
    implementation("com.github.yalantis:ucrop:2.2.6")

    //reaktive
    implementation("com.badoo.reaktive:reaktive:1.2.1")
    implementation("com.badoo.reaktive:reaktive-annotations:1.2.1")
    implementation("com.badoo.reaktive:coroutines-interop:1.2.1")

    // Image Loader
    implementation("io.coil-kt:coil:2.2.2")

    implementation("com.github.bumptech.glide:glide:4.14.2")
    annotationProcessor("com.github.bumptech.glide:compiler:4.14.2")
}

configurations.implementation {
    exclude(group = "org.jetbrains.kotlin", module = "kotlin-stdlib-jdk7")
    exclude(group = "org.jetbrains.kotlin", module = "kotlin-stdlib-jdk8")
}