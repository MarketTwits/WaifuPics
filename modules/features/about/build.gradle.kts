plugins {
     alias(libs.plugins.kotlin.multiplatform.compose.convention)
}

kotlin{
    android {
        namespace = "com.markettwits.about"
    }

    sourceSets.commonMain.dependencies {
        implementation(project(path = ":modules:core:core-ui"))
        implementation(libs.compose.resources)
    }
}