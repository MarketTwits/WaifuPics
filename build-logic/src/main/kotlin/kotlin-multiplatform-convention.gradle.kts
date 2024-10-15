import extension.libs

plugins {
    id("com.android.library")
    id("kotlin-android-convention")
    id("org.jetbrains.kotlin.multiplatform")
}

kotlin {
    jvm()
    jvmToolchain(libs.versions.jvm.get().toString().toInt())
    androidTarget()

    wasmJs() {
        browser()
        binaries.executable()
    }

    js() {
        browser()
        nodejs()
        binaries.executable()
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "WaifuPics"
            isStatic = true
        }
    }

    sourceSets {
        all {
            languageSettings.optIn("kotlinx.cinterop.ExperimentalForeignApi")
        }
    }
}