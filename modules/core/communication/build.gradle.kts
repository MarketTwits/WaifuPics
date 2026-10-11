
plugins {
    alias(libs.plugins.kotlin.multiplatform.convention)
}

kotlin {
    android {
        namespace = "com.markettwits.waifupics.communication"
    }

    sourceSets.commonMain.dependencies {
        implementation(libs.kotlinx.coroutines.core)
    }
}