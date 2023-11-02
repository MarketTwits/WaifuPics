plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
}

android {

    namespace = Config.SDK.namespace
    compileSdk = Config.SDK.compileSdk

    defaultConfig {
        applicationId = Config.SDK.namespace
        minSdk = Config.SDK.minSdk
        targetSdk = Config.SDK.targetSdk
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    kotlin{
        jvmToolchain(Config.JDK.jvm)
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.asProvider().get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":random_image"))
    implementation(project(path = ":core-ui"))
    implementation(project(path = ":navigation"))
    implementation(project(path = ":gallery"))
}
