plugins {
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.multiplatform.convention)
}

kotlin {
    android {
        namespace = "com.markettwits.waifupics.paths"
    }

    sourceSets.androidMain.dependencies {
        implementation(libs.context.provider)
    }
}