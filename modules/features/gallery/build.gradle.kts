plugins {
     alias(libs.plugins.kotlin.multiplatform.compose.convention)
}

android {
    namespace = "com.markettwits.gallery"
}
kotlin{
    sourceSets.commonMain.dependencies {
        implementation(project(path = ":modules:features:cache-datasource"))
        implementation(project(path = ":modules:core:communication"))
        implementation(project(path = ":modules:core:core-ui"))
        implementation(project(path = ":modules:core:async"))
        implementation(project(path = ":modules:core:image-action:core"))
        implementation(compose.components.resources)
        implementation(libs.kotlinx.datetime)
        implementation(libs.koin.core)
    }
}