plugins {
    alias(libs.plugins.kotlin.multiplatform.compose.convention)
}

android {
    namespace = "com.markettwits.random.filter"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(path = ":modules:core:core-ui"))
            implementation(project(path = ":modules:core:communication"))
            implementation(libs.koin.core)
            implementation(compose.components.resources)
        }
    }
}