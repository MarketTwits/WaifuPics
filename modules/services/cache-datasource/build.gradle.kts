plugins {
    alias(libs.plugins.android.library.convention)
    alias(libs.plugins.realm)
}


android {
    namespace = "com.markettwits.cache_datasource"
}

dependencies {
    implementation(project(":modules:services:core"))
    implementation(libs.realm)
    implementation(libs.core.ktx)
    implementation(libs.compose.coil)
    testImplementation(libs.junit)
}