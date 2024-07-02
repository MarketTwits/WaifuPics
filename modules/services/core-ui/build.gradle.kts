
plugins {
    id("kotlin-multiplatform-compose-convetion")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.markettwits.core_ui"

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
kotlin {
    sourceSets{
        androidMain.dependencies {
            api(libs.ktor.client.okhttp)
        }
        commonMain.dependencies {
            api(compose.runtime)
            api(compose.foundation)
            api(compose.material3)
            api(compose.materialIconsExtended)
            api(compose.ui)
            api(compose.components.uiToolingPreview)
            api(libs.lifecycle.viewmodel.compose)
            implementation(compose.components.resources)
            implementation(project(":modules:services:core"))
            api(libs.ktor.core)
            api(libs.coil.compose.core)
            api(libs.coil.compose)
            api(libs.coil.mp)
            api(libs.coil.network.ktor)
            api(libs.kotlinx.serialization.json)
        }
        jvmMain.dependencies {
            api(libs.ktor.client.okhttp)
            api(libs.coil.network.ktor)
        }

    }
}