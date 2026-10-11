plugins {
    alias(libs.plugins.kotlin.multiplatform.compose.convention)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    android {
        namespace = "com.markettwits.waifupics.theme"
    }

    sourceSets {
        androidMain.dependencies {
            api(libs.ktor.client.okhttp)
        }
        commonMain.dependencies {
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
            implementation(libs.compose.resources)
            implementation(project(path = ":modules:core:async"))
            implementation(project(path = ":modules:features:settings"))
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.kotlinx.coroutines.test)
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
