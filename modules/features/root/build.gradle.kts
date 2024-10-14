plugins {
    id("kotlin-multiplatform-compose-convetion")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.markettwits.root"
}
kotlin{
    sourceSets.commonMain.dependencies {
        implementation(libs.koin.core)
        implementation(libs.koin.compose)
        implementation(libs.compose.navigation)
        implementation(libs.kotlinx.serialization.core)
        implementation(project(path = ":modules:features:random_image"))
        implementation(project(path = ":modules:core:core-ui"))
        implementation(project(path = ":modules:core:async"))
        implementation(project(path = ":modules:features:navigation"))
        implementation(project(path = ":modules:features:gallery"))
        implementation(project(path = ":modules:features:about"))
    }
}