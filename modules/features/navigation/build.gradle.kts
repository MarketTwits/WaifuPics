plugins {
    alias(libs.plugins.android.library.compose.convention)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "com.markettwits.navigation"
}

dependencies {
    implementation(project(":modules:services:core-ui"))
    implementation(libs.retrofit.converter)
    implementation(libs.onebone.toolbar)
}