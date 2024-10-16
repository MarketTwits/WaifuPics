plugins {
    alias(libs.plugins.kotlin.multiplatform.compose.convention)
}

android {
    namespace = "com.markettwits.random.report"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(path = ":modules:core:core-ui"))
            implementation(project(path = ":modules:core:result"))
            implementation(compose.components.resources)
        }
    }
}