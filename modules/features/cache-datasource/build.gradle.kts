plugins {
    id("kotlin-multiplatform-convention")
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    android {
        namespace = "com.markettwits.cache_datasource"
    }

    sourceSets {
        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.kotlinx.coroutines.test)
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
