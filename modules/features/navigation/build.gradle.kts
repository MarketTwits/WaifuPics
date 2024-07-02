plugins {
    id("kotlin-multiplatform-compose-convetion")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.markettwits.navigation"

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

kotlin{
    sourceSets.commonMain.dependencies {
        implementation(project(":modules:services:core-ui"))
        implementation(project(":modules:services:core"))
        implementation(libs.kotlinx.serialization.core)
        implementation(compose.components.resources)
    }

}