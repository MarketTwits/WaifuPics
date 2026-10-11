@file:OptIn(ExperimentalWasmDsl::class)

import extension.libs
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    id("com.android.kotlin.multiplatform.library")
    id("org.jetbrains.kotlin.multiplatform")
}

kotlin {
    jvm()
    jvmToolchain(libs.versions.jvm.get().toInt())
    android {
        compileSdk = extension.model.ApkConfig.COMPILE_SDK_VERSION
        minSdk = extension.model.ApkConfig.MIN_SDK_VERSION
        androidResources.enable = true
        withHostTest {}
    }

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
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "WaifuPics"
            isStatic = true
        }
    }

}
