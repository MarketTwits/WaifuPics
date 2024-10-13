plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
}

kotlin {
    @OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)
    wasmJs {
        moduleName = "composeApp"
        browser {
            commonWebpackConfig {
                outputFileName = "composeApp.js"
            }
        }
        binaries.executable()
    }
    js(IR) {
        useEsModules()
        browser{
            commonWebpackConfig {
                outputFileName = "web.js"
            }
        }
        binaries.executable()
    }


    sourceSets.commonMain.dependencies {
        implementation(project(path = ":modules:features:root"))
        implementation(project(path = ":modules:features:core-ui"))
        implementation(project(path = ":modules:features:async"))
    }
}
