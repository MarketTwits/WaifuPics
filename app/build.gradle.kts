import DependenciesGroup.Retrofit.okkhttpImpl
import DependenciesGroup.Retrofit.retrofitImpl

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id ("kotlin-parcelize")
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

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = DependenciesConfig.Compose.composeVersion
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    val okhttp_ver = "4.10.0"

    implementation(project(path = ":core-ui"))
    implementation(project(path = ":navigation"))
    retrofitImpl()
    okkhttpImpl()
    implementation(DependenciesConfig.Zoomable.fetch())
    //implementation(DependenciesConfig.KotlinCore.fetch())
}