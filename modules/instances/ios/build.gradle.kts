
plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
}

kotlin{

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

    jvmToolchain(17)

    sourceSets.iosMain.dependencies {
        implementation(project(path = ":modules:features:root"))
        implementation(project(path = ":modules:services:core-ui"))
        implementation(project(path = ":modules:services:core"))
        implementation(project(path = ":modules:services:cache-datasource"))
    }
}