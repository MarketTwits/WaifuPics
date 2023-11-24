
plugins {
    alias(libs.plugins.android.library.compose.convention)
}

android {
    namespace = "com.markettwits.core_ui"
}

dependencies {
    api("androidx.core:core-splashscreen:1.0.1")
    api(project(path = ":modules:services:core"))
    api(libs.bundles.composeUiBundle)
    api(libs.junit.ext.ktx)
    debugApi(libs.bundles.composeUiBundleDebug)

}