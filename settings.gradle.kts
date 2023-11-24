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
include(":modules:services:core")
include(":modules:services:core-ui")
include(":modules:services:cache-datasource")
include(":modules:features:gallery")
include(":modules:features:random_image")
include(":modules:features:about")
include(":modules:features:navigation")
