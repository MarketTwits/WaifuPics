plugins {
    id("kotlin-multiplatform-convention")
}

android {
    namespace = "com.markettwits.core"

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

kotlin{
    sourceSets.commonMain.dependencies {
        api(libs.kotlinx.coroutines.core)
        api(libs.lifecycle.viewmodel)
    }
}