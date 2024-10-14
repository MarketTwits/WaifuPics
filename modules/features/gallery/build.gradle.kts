plugins {
    id("kotlin-multiplatform-compose-convetion")
}

android {
    namespace = "com.markettwits.gallery"
}
kotlin{
    sourceSets.commonMain.dependencies {
        implementation(project(":modules:features:cache-datasource"))
        implementation(project(":modules:core:core-ui"))
        implementation(project(":modules:core:async"))
        implementation(project(":modules:core:image_action:core"))
        implementation(compose.components.resources)
        implementation(libs.kotlinx.datetime)
        implementation(libs.koin.core)
    }
}