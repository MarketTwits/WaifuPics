plugins {
    alias(libs.plugins.kotlin.multiplatform.compose.convention)
}

kotlin {
    android {
        namespace = "com.markettwits.random.report"
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.modules.core.coreUi)
            implementation(projects.modules.core.result)
            implementation(projects.modules.core.async)
            implementation(projects.modules.features.random.cloud)
            implementation(libs.compose.resources)
        }
    }
}