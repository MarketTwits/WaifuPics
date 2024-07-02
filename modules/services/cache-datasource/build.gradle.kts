plugins {
    id("kotlin-multiplatform-convention")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.markettwits.cache_datasource"

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kstore)
            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.serialization.core)
            implementation(libs.kotlinx.coroutines.core)
        }
        androidMain.dependencies {
            implementation(libs.kstore.file)
        }
        jvmMain.dependencies {
            implementation(libs.kstore.file)
        }
        wasmJsMain.dependencies {
            implementation(libs.kstore.storage)
        }
        jsMain.dependencies {
            implementation(libs.kstore.storage)
        }
    }
}
