plugins {
    id("kotlin-multiplatform-compose-convetion")
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "com.markettwits.navigation"

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

kotlin{
    sourceSets.androidMain.dependencies {
        implementation(project(":modules:services:core-ui"))
        implementation(project(":modules:services:core"))
        implementation(libs.onebone.toolbar)
    }

}