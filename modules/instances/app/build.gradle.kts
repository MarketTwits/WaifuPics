import extension.model.ApkConfig

plugins {
    id("android-application-convention")
    id("org.jetbrains.kotlin.plugin.compose")
}

android.namespace = ApkConfig.APPLICATION_ID

dependencies {
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.compose.activity)
    implementation(libs.core.splashscreen)
    implementation(project(path = ":modules:core:core-ui"))
    implementation(project(path = ":modules:features:root"))
}
