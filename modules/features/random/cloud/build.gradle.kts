plugins {
    alias(libs.plugins.kotlin.multiplatform.convention)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    android {
        namespace = "com.markettwits.random.cloud"
    }

    sourceSets {
        commonMain.dependencies {
            implementation(project(path = ":modules:core:cloud"))
        }
    }
}