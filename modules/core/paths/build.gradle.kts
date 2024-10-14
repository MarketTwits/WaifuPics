plugins {
    id("kotlin-multiplatform-convention")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.markettwits.waifupics.cache"

}
kotlin {
    sourceSets.commonMain.dependencies {
        api(libs.kotlinx.io.core)
    }
    sourceSets.androidMain.dependencies {
        implementation(libs.context.provider)
    }
}