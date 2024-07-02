import org.jetbrains.compose.desktop.application.dsl.TargetFormat

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
        compilations.all {
            kotlinOptions.jvmTarget = "20"
        }
        withJava()
    }

    jvmToolchain(20)

    sourceSets.jvmMain.dependencies {
        implementation(project(path = ":modules:features:root"))
        implementation(project(path = ":modules:services:core-ui"))
        implementation(project(path = ":modules:services:core"))
        implementation(project(":modules:services:cache-datasource"))
        implementation(compose.desktop.currentOs)
        implementation(compose.desktop.common)
    }
}

compose.desktop {
    application {
        mainClass = "com.markettwits.waifupics.app.MainKt"
        nativeDistributions {
            packageName = "WaifuPics"
            description = "Sportsauce Desktop Application"
            copyright = "© 2024 My Name. All rights reserved."
            vendor = "MarketTwits"
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            appResourcesRootDir.set(project.layout.projectDirectory.dir("resources"))
        }
        buildTypes.release.proguard {
            configurationFiles.from("compose-desktop.pro")
            obfuscate.set(false)
            optimize.set(false)
            version.set("7.5.0")
        }
    }
}
