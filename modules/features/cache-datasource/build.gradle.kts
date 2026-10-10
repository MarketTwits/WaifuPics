plugins {
    id("kotlin-multiplatform-convention")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.markettwits.cache_datasource"

}
kotlin {
    sourceSets {
        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.10.1")
        }
        commonMain.dependencies {
            implementation(libs.koin.core)
            implementation(libs.kstore)
            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.serialization.core)
            implementation(libs.kotlinx.coroutines.core)
            implementation(project(path = ":modules:core:cache"))
            implementation(project(path = ":modules:core:paths"))
        }
    }
}
