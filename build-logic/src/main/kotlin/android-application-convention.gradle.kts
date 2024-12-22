import extension.model.ApkConfig

plugins {
    id("com.android.application")
    id("kotlin-android-convention")
    id("kotlin-android")
}
android{
    defaultConfig {
        applicationId = ApkConfig.APPLICATION_ID
    }
}