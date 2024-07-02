pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://maven.pkg.jetbrains.space/kotlin/p/wasm/experimental")
        maven( "https://androidx.dev/storage/compose-compiler/repository")
        maven("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/dev")

    }
}
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://maven.pkg.jetbrains.space/kotlin/p/wasm/experimental")
        maven( "https://androidx.dev/storage/compose-compiler/repository")
        maven("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/dev")
    }
}

includeBuild("build-logic")

rootProject.name = "WaifuPics"
include(":modules:instances:app")
include(":modules:instances:desktop")
include(":modules:instances:web")
include(":modules:services:core")
include(":modules:services:core-ui")
include(":modules:services:cloud-datasource")
include("modules:features:root")
include(":modules:services:cache-datasource")
include(":modules:features:gallery")
include(":modules:features:random_image")
include(":modules:features:about")
include(":modules:features:navigation")
include(":modules:services:image_action:core")
include(":modules:services:image_action:compose")
