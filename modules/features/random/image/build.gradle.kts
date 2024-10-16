plugins {
    alias(libs.plugins.kotlin.multiplatform.compose.convention)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.markettwits.random.image"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(path = ":modules:core:async"))
            implementation(project(path = ":modules:core:core-ui"))
            implementation(project(path = ":modules:core:image-action:core"))
            implementation(project(path = ":modules:core:communication"))
            implementation(project(path = ":modules:core:result"))
            implementation(project(path = ":modules:features:cache-datasource"))
            implementation(project(path = ":modules:features:random:cloud"))
            implementation(project(path = ":modules:features:random:filter"))
            implementation(project(path = ":modules:features:random:report"))
            implementation(libs.koin.core)
            implementation(compose.components.resources)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.serialization.core)
        }
    }
}