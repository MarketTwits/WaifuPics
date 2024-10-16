plugins {
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.multiplatform.compose.convention)
}

android {
    namespace = "com.markettwits.root"
}
kotlin {
    sourceSets.commonMain.dependencies {
        implementation(libs.koin.core)
        implementation(libs.koin.compose)
        implementation(libs.compose.navigation)
        implementation(libs.kotlinx.serialization.core)
        implementation(project(path = ":modules:features:random:image"))
        implementation(project(path = ":modules:core:core-ui"))
        implementation(project(path = ":modules:core:async"))
        implementation(project(path = ":modules:features:navigation"))
        implementation(project(path = ":modules:features:gallery"))
        implementation(project(path = ":modules:features:about"))
    }
}