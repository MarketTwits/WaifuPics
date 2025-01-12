
plugins {
    alias(libs.plugins.kotlin.multiplatform.convention)
}

android{
    namespace = "com.markettwits.waifupics.communication"
}

kotlin {
    sourceSets.commonMain.dependencies {
        implementation(libs.kotlinx.coroutines.core)
    }
}