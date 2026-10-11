plugins {
    id("kotlin-multiplatform-convention")
}

kotlin{
    android {
        namespace = "com.markettwits.image_action_core"
    }

    sourceSets {
        iosMain {
            languageSettings.optIn("kotlinx.cinterop.ExperimentalForeignApi")
        }
        iosArm64Main {
            languageSettings.optIn("kotlinx.cinterop.ExperimentalForeignApi")
        }
        iosSimulatorArm64Main {
            languageSettings.optIn("kotlinx.cinterop.ExperimentalForeignApi")
        }
    }

    sourceSets.commonMain.dependencies {
        implementation(libs.koin.core)
        implementation(libs.filekit.core)
        implementation(libs.coil.mp)
    }
    sourceSets.androidMain.dependencies {
        implementation(libs.core.ktx)
        implementation(libs.appcompat)
    }
    sourceSets.jvmMain.dependencies {
        implementation(libs.jna)
        implementation(libs.jna.platform)
    }
    sourceSets.wasmJsMain.dependencies {
        implementation(libs.kotlinx.browser)
    }
}
