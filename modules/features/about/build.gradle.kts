plugins {
     alias(libs.plugins.kotlin.multiplatform.compose.convention)
}

android {
    namespace = "com.markettwits.about"
}
kotlin{
    sourceSets.commonMain.dependencies {
        implementation(project(path = ":modules:core:core-ui"))
        implementation(compose.components.resources)
    }
}