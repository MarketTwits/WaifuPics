plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.markettwits.core"
    compileSdk = Config.SDK.compileSdk

    defaultConfig {
        minSdk = Config.SDK.minSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
    kotlin{
        jvmToolchain(Config.JDK.jvm)
    }
    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

}

dependencies {
    api("androidx.appcompat:appcompat:1.6.1")
}