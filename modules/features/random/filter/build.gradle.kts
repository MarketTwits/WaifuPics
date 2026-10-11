plugins {
    alias(libs.plugins.kotlin.multiplatform.compose.convention)
}

kotlin {
    android {
        namespace = "com.markettwits.random.filter"
    }

    sourceSets {
        commonMain.dependencies {
            implementation(project(path = ":modules:core:core-ui"))
            implementation(project(path = ":modules:core:communication"))
            implementation(libs.koin.core)
            implementation(libs.compose.resources)
        }
    }
}