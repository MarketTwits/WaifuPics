plugins {
    id("kotlin-multiplatform-compose-convetion")
}

android {
    namespace = "com.markettwits.gallery"

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

}
kotlin{
    sourceSets.commonMain.dependencies {
        implementation(project(":modules:services:cache-datasource"))
        implementation(project(":modules:services:core-ui"))
        implementation(project(":modules:services:core"))
        implementation(project(":modules:services:image_action:core"))
        implementation(compose.components.resources)
        implementation(libs.kotlinx.datetime)
        implementation(libs.koin.core)
    }
}