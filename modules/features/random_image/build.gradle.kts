plugins {
    id("kotlin-multiplatform-compose-convetion")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.markettwits.random_image"
}

kotlin{
    sourceSets{
        commonMain.dependencies {
            implementation(project(":modules:services:core"))
            implementation(project(":modules:services:core-ui"))
            implementation(project(":modules:services:cache-datasource"))
            implementation(project(":modules:services:cloud-datasource"))
            implementation(project(":modules:services:image_action:core"))
            implementation(libs.koin.core)
            implementation(compose.components.resources)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.serialization.core)
            implementation("media.kamel:kamel-image:1.0.0-beta.4")
        }
    }

}