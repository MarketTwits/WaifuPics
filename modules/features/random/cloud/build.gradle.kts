plugins {
    alias(libs.plugins.kotlin.multiplatform.convention)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.markettwits.random.cloud"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(path = ":modules:core:cloud"))
        }
    }
}