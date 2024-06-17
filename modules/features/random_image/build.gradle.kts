plugins {
    id("kotlin-multiplatform-compose-convetion")
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlin.serialization)
    id("com.google.devtools.ksp") version "2.0.0-1.0.22"
    id("de.jensklingenberg.ktorfit") version "2.0.0"
}

android {
    namespace = "com.markettwits.random_image"
}

kotlin{
    sourceSets.commonMain.dependencies {
        implementation(project(":modules:services:core"))
        implementation(project(":modules:services:core-ui"))
        implementation(project(":modules:services:cache-datasource"))
        implementation(project(":modules:services:image_action"))
        //implementation(libs.bundles.network)
        implementation(compose.components.resources)
        implementation(libs.ktorfit.lib)
        implementation(libs.ktor.client.content.negotiation)
        implementation(libs.ktor.client.okhttp)
        implementation(libs.ktor.client.json)
        implementation(libs.ktor.core)
        implementation(libs.kotlinx.serialization.json)
        implementation(libs.kotlinx.serialization.core)
        implementation(libs.zoomable)
    }
}