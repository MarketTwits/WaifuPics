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
include(":modules:instances:ios")
include(":modules:features:cloud-datasource")
include("modules:features:root")
include(":modules:features:cache-datasource")
include(":modules:features:gallery")
include(":modules:features:random_image")
include(":modules:features:about")
include(":modules:features:navigation")
include(":modules:core:async")
include(":modules:core:image-action:core")
include(":modules:core:cache")
include(":modules:core:paths")
include(":modules:core:core-ui")
include(":modules:core:communication")
include(":modules:core:theme")
include(":modules:core:cloud")
include(":modules:features:settings")
include(":modules:features:random:cloud")
include(":modules:features:random:image")
include(":modules:features:random:report")
include(":modules:features:random:filter")
include(":modules:core:result")
