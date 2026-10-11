plugins {
    alias(libs.plugins.kotlin.multiplatform.compose.convention)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    android {
        namespace = "com.markettwits.waifupics.core_ui"
    }

    sourceSets {
        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.kotlinx.coroutines.test)
        }
        androidMain.dependencies {
            api(libs.ktor.client.okhttp)
        }
        commonMain.dependencies {
            implementation(libs.compose.resources)
            api(libs.koin.compose)
            api(libs.compose.runtime)
            api(libs.compose.foundation)
            api(libs.compose.material3)
            api(libs.compose.material.icons.extended)
            api(libs.compose.ui)
            api(libs.compose.ui.tooling.preview)
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
