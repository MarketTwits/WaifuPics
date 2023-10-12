plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.realm)
}

android {
    namespace = "com.markettwits.image_cache_datasource"
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

}

dependencies {
    implementation(project(":core"))
    implementation(libs.realm)
    implementation(libs.core.ktx)
    testImplementation(libs.junit)
}