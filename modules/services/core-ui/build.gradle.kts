
plugins {
    id("kotlin-multiplatform-compose-convetion")
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
        all {
            languageSettings {
                optIn("org.jetbrains.compose.resources.ExperimentalResourceApi")
            }
        }

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
            api(libs.coil.compose)
            api(libs.coil.network.ktor)
            api(libs.lifecycle.viewmodel)
            api(libs.lifecycle.viewmodel.compose)
            implementation(compose.components.resources)
            implementation(project(":modules:services:core"))
        }

    }
}