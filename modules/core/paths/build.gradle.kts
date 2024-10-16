plugins {
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.multiplatform.convention)
}

android {
    namespace = "com.markettwits.waifupics.paths"
}
kotlin {
    sourceSets.androidMain.dependencies {
        implementation(libs.context.provider)
    }
}