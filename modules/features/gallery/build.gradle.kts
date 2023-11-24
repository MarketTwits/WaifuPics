plugins {
    alias(libs.plugins.android.library.compose.convention)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "com.markettwits.gallery"

}

dependencies {
    implementation(project(":modules:services:cache-datasource"))
    implementation(project(":modules:services:core-ui"))
    implementation(project(":modules:services:image_action"))
    implementation(libs.nabla.gallery)
    implementation(libs.zoomable)
}