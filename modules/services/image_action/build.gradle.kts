plugins {
    alias(libs.plugins.android.library.convention)
}

android {
    namespace = "com.markettwits.image_action"
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
}