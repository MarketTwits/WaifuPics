plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.markettwits.random_image"
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

    implementation(project(":modules:services:core-ui"))
    implementation(project(":modules:services:cache-datasource"))
    implementation(project(":modules:services:image_action"))
    implementation(libs.bundles.network)
    implementation(libs.zoomable)
}