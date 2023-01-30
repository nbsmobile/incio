@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "com.nbs.kmm.sample.android"
    compileSdk = 33
    defaultConfig {
        applicationId = "com.nbs.kmm.sample.android"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
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
    implementation("androidx.compose.ui:ui:1.4.0-alpha05")
    implementation("androidx.compose.material:material:1.4.0-alpha05")
    implementation("androidx.compose.ui:ui-tooling:1.4.0-alpha05")
    implementation("androidx.compose.runtime:runtime-livedata:1.4.0-alpha04")

    //timber
    implementation("com.jakewharton.timber:timber:4.7.1")

    //rxjava
    implementation("io.reactivex.rxjava2:rxjava:2.2.21")
    implementation("io.reactivex.rxjava2:rxandroid:2.1.1")

    //gson
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
}

configurations.implementation {
    exclude(group = "org.jetbrains.kotlin", module = "kotlin-stdlib-jdk7")
    exclude(group = "org.jetbrains.kotlin", module = "kotlin-stdlib-jdk8")
}