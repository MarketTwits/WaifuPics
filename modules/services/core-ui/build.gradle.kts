
plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.markettwits.core_ui"
    compileSdk = Config.SDK.compileSdk

    defaultConfig {
        minSdk = Config.SDK.minSdk

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
        kotlinCompilerExtensionVersion  = libs.versions.compose.asProvider().get()
    }

}

dependencies {
    api("androidx.core:core-splashscreen:1.0.1")
    api(project(path = ":modules:services:core"))
    api(libs.bundles.composeUiBundle)
    api(libs.junit.ext.ktx)
    debugApi(libs.bundles.composeUiBundleDebug)

}