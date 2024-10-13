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
            implementation(project(":modules:features:async"))
            implementation(project(":modules:features:core-ui"))
            implementation(project(":modules:features:cache-datasource"))
            implementation(project(":modules:features:cloud-datasource"))
            implementation(project(":modules:features:image_action:core"))
            implementation(libs.koin.core)
            implementation(compose.components.resources)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.serialization.core)
        }
    }

}