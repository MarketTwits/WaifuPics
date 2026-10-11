
plugins {
    alias(libs.plugins.kotlin.multiplatform.convention)
}

kotlin{
    android {
        namespace = "com.markettwits.async"
    }

    sourceSets.commonMain.dependencies {
        api(libs.kotlinx.coroutines.core)
    }
}