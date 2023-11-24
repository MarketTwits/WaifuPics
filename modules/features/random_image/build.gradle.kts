plugins {
    alias(libs.plugins.android.library.compose.convention)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.markettwits.random_image"
}

dependencies {

    implementation(project(":modules:services:core-ui"))
    implementation(project(":modules:services:cache-datasource"))
    implementation(project(":modules:services:image_action"))
    implementation(libs.bundles.network)
    implementation(libs.zoomable)
}