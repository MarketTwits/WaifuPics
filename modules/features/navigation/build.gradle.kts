plugins {
     alias(libs.plugins.kotlin.multiplatform.compose.convention)
}

android {
    namespace = "com.markettwits.navigation"
}

kotlin{
    sourceSets.commonMain.dependencies {
        implementation(project(path = ":modules:core:core-ui"))
        implementation(project(path = ":modules:core:async"))
        implementation(project(path = ":modules:core:communication"))
        implementation(project(path = ":modules:features:settings"))
        implementation(compose.components.resources)
        implementation(libs.koin.core)
    }
}