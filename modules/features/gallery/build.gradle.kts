plugins {
    id("kotlin-multiplatform-compose-convetion")
}

android {
    namespace = "com.markettwits.gallery"
}
kotlin{
    sourceSets.commonMain.dependencies {
        implementation(project(":modules:features:cache-datasource"))
        implementation(project(":modules:features:core-ui"))
        implementation(project(":modules:features:async"))
        implementation(project(":modules:features:image_action:core"))
        implementation(compose.components.resources)
        implementation(libs.kotlinx.datetime)
        implementation(libs.koin.core)
    }
}