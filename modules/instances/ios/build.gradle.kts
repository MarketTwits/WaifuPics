
plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
}

kotlin{

    listOf(
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
        implementation(project(path = ":modules:core:core-ui"))
        implementation(project(path = ":modules:core:async"))
        implementation(project(path = ":modules:features:cache-datasource"))
    }
}
