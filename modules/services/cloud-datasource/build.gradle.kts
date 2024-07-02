plugins {
    id("kotlin-multiplatform-compose-convetion")
//    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlin.serialization)
//    id("com.google.devtools.ksp") version "2.0.0-1.0.22"
//    id("de.jensklingenberg.ktorfit") version "2.0.0"
}

android {
    namespace = "com.markettwits.cloud_datasource"
}

kotlin{
    sourceSets{
        commonMain.dependencies {
            implementation(project(":modules:services:core"))
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.core)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.client.serialization.json)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.serialization.core)
        }
        jvmMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }
    }

}