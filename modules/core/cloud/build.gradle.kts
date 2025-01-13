plugins {
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.multiplatform.convention)
}

android {
    namespace = "com.markettwits.waifupics.cloud"
}

kotlin{
    sourceSets{
        commonMain.dependencies {
            api(libs.koin.core)
            api(libs.ktor.core)
            api(libs.ktor.client.logging)
            api(libs.ktor.client.content.negotiation)
            api(libs.ktor.client.serialization.json)
            api(libs.kotlinx.serialization.json)
            api(libs.kotlinx.serialization.core)
        }
        jvmMain.dependencies {
            api(libs.ktor.client.okhttp)
        }
//        androidMain.dependencies {
//            api(libs.ktor.client.okhttp)
//        }
        iosMain.dependencies {
            api(libs.ktor.client.darwin)
        }
    }

}