plugins {
    alias(libs.plugins.android.library.convention)
}

android {
    namespace = "com.markettwits.core"
}
dependencies {
    api("androidx.appcompat:appcompat:1.6.1")
}