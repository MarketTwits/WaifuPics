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
            implementation(projects.modules.core.result)
            implementation(projects.modules.core.communication)
            implementation(projects.modules.core.imageAction.core)
            implementation(projects.modules.core.coreUi)
            implementation(projects.modules.core.async)

            implementation(projects.modules.features.cacheDatasource)
            implementation(projects.modules.features.random.cloud)
            implementation(projects.modules.features.random.report)
            implementation(projects.modules.features.random.filter)
            implementation(projects.modules.features.random.report)

            implementation(libs.koin.core)
            implementation(compose.components.resources)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.serialization.core)
        }
    }
}