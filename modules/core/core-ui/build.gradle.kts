plugins {
    alias(libs.plugins.kotlin.multiplatform.compose.convention)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.markettwits.waifupics.core_ui"
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            api(libs.ktor.client.okhttp)
        }
        commonMain.dependencies {
            implementation(compose.components.resources)
            api(libs.koin.compose)
            api(compose.runtime)
            api(compose.foundation)
            api(compose.material3)
            api(compose.materialIconsExtended)
            api(compose.ui)
            api(compose.components.uiToolingPreview)
            api(libs.compose.lifecycle.viewmodel)
            api(libs.ktor.core)
            api(libs.coil.compose.core)
            api(libs.coil.compose)
            api(libs.coil.mp)
            api(libs.coil.network.ktor)
            api(libs.kotlinx.serialization.json)
            api(project(path = ":modules:core:theme"))
        }
        jvmMain.dependencies {
            api(libs.ktor.client.okhttp)
        }
        wasmJsMain.dependencies {
            api(libs.ktor.client.js)
        }
        jsMain.dependencies {
            api(libs.ktor.client.js)
        }
    }
}