import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
}
tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "com.markettwits.waifupics.app.MainKt"
    }
}

kotlin {

    jvm {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    jvmToolchain(17)

    sourceSets.jvmMain.dependencies {
        implementation(project(path = ":modules:features:root"))
        implementation(project(path = ":modules:core:core-ui"))
        implementation(project(":modules:features:cache-datasource"))
        implementation(libs.kotlinx.coroutines.swing)
        implementation(compose.desktop.currentOs)
        implementation(libs.compose.desktop.common)
    }
}

compose.desktop {
    application {
        mainClass = "com.markettwits.waifupics.app.MainKt"
        nativeDistributions {
            packageName = "WaifuPics"
            packageVersion = libs.versions.versionName.get()
            description = "WaifuPics desktop application"
            copyright = "© 2026 MarketTwits"
            vendor = "MarketTwits"
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            appResourcesRootDir.set(project.layout.projectDirectory.dir("resources"))
            macOS {
                bundleID = "com.markettwits.waifupics"
                iconFile.set(project.file("resources/macos/WaifuPics.icns"))
            }
            windows {
                iconFile.set(project.file("resources/windows/WaifuPics.ico"))
            }
            linux {
                iconFile.set(project.file("resources/linux/WaifuPics.png"))
            }
        }
        buildTypes.release.proguard {
            isEnabled.set(true)
            configurationFiles.from("compose-desktop.pro")
            version.set("7.8.0")
            obfuscate.set(true)
            // ProGuard optimization emits invalid bytecode for the desktop app
            // (VerifyError: Bad return type) on release startup. Keep shrinking
            // and obfuscation enabled while skipping bytecode optimization.
            optimize.set(false)
        }
    }
}
