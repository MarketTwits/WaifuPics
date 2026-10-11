plugins {
     alias(libs.plugins.kotlin.multiplatform.compose.convention)
}

kotlin{
    android {
        namespace = "com.markettwits.navigation"
    }

    sourceSets.commonMain.dependencies {
        implementation(project(path = ":modules:core:core-ui"))
        implementation(project(path = ":modules:core:async"))
        implementation(project(path = ":modules:core:communication"))
        implementation(project(path = ":modules:features:settings"))
        implementation(libs.compose.resources)
        implementation(libs.compose.ui.backhandler)
        implementation(libs.koin.core)
    }
}
