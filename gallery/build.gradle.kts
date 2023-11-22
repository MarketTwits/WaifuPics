@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "com.markettwits.gallery"
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
    implementation("androidx.compose.runtime:runtime-livedata:1.5.2")
    implementation(project(":cache-datasource"))
    implementation(project(":core-ui"))
    implementation(project(":navigation"))
    implementation(libs.zoomable)
    implementation("run.nabla:gallery-picker:1.4.5")
    implementation(libs.androidx.exifinterface)

}