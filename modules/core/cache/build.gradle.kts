plugins {
    alias(libs.plugins.kotlin.multiplatform.convention)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.markettwits.waifupics.cache"
}
kotlin{
    sourceSets.commonMain.dependencies {
        implementation(libs.koin.core)
        implementation(libs.kstore)
        implementation(libs.kotlinx.serialization.json)
    }
    sourceSets.androidMain.dependencies {
        implementation(libs.kstore.file)
    }
    sourceSets.jvmMain.dependencies {
        implementation(libs.kstore.file)
    }
    sourceSets.wasmJsMain.dependencies {
        implementation(libs.kstore.storage)
    }
    sourceSets.jsMain.dependencies {
        implementation(libs.kstore.storage)
    }
    sourceSets.iosMain.dependencies {
        implementation(libs.kstore.file)
    }
}