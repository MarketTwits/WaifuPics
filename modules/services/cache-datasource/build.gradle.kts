plugins {
    alias(libs.plugins.android.library.convention)
    alias(libs.plugins.realm)
    alias(libs.plugins.ksp)
}


android {
    namespace = "com.markettwits.cache_datasource"
}

dependencies {
    implementation(project(":modules:services:core"))
    implementation(libs.realm)
    implementation ("io.realm.kotlin:library-base:1.11.0")
    implementation ("io.realm.kotlin:library-sync:1.11.0")
    implementation(libs.core.ktx)
    implementation(libs.compose.coil)
    testImplementation(libs.junit)
}