plugins {
    alias(libs.plugins.kotlin.multiplatform.compose.convention)
}

android {
    namespace = "com.markettwits.random.report"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.modules.core.coreUi)
            implementation(projects.modules.core.result)
            implementation(projects.modules.core.async)
            implementation(projects.modules.features.random.cloud)
            implementation(compose.components.resources)
        }
    }
}