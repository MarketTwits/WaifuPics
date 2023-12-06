plugins {
    alias(libs.plugins.android.library.compose.convention)
}

android {
    namespace = "com.markettwits.about"

}

dependencies {
    implementation(project(":modules:services:core-ui"))
}