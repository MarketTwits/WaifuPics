import extension.model.ApkConfig
import extension.libs

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
kotlin {
    jvmToolchain(libs.versions.jvm.get().toInt())
}
