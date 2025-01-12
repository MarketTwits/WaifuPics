plugins {
    alias(libs.plugins.kotlin.multiplatform.convention)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.markettwits.settings"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.koin.core)
            implementation(libs.kstore)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.coroutines.core)
            implementation(project(path = ":modules:core:cache"))
            implementation(project(path = ":modules:core:paths"))
        }
    }
}
