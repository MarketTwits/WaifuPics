plugins {
    id("kotlin-multiplatform-compose-convetion")
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "com.markettwits.gallery"

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

}


kotlin{
    sourceSets.androidMain.dependencies {
        implementation(project(":modules:services:cache-datasource"))
        implementation(project(":modules:services:core-ui"))
        implementation(project(":modules:services:core"))
        implementation(project(":modules:services:image_action"))
        implementation(libs.zoomable)
    }
}