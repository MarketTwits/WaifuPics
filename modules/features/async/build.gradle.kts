plugins {
    id("kotlin-multiplatform-convention")
}

android {
    namespace = "com.markettwits.async"
}

kotlin{
    sourceSets.commonMain.dependencies {
        api(libs.kotlinx.coroutines.core)
    }
}