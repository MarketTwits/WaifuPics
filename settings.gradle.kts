pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "WaifuPics"
include(":app")
include(":core")
include(":core-ui")
include(":navigation")
include(":random_image")
include(":gallery")
include(":cache-datasource")
include(":about")
