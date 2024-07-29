import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

plugins {
    id("com.android.library")
    id("base-android-convention")
    id("org.jetbrains.kotlin.multiplatform")
}

kotlin {
    jvm()
    jvmToolchain(localLibs.findVersion("jvm").get().toString().toInt())
    androidTarget()
    @OptIn(ExperimentalWasmDsl::class)
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

fun versionCode(versionName: String): Int {
    val components = versionName.split(".")
    val major = components.getOrNull(0)?.toIntOrNull()
        ?: throw IllegalArgumentException("major version in version name not found")
    val minor = components.getOrNull(1)?.toIntOrNull()
        ?: throw IllegalArgumentException("minor version in version name not found")
    val patch = components.getOrNull(2)?.toIntOrNull()
        ?: throw IllegalArgumentException("patch version in version name not found")
    return major * 10000 + minor * 100 + patch
}
