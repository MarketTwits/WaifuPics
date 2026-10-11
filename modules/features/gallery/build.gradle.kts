plugins {
     alias(libs.plugins.kotlin.multiplatform.compose.convention)
}

kotlin{
    android {
        namespace = "com.markettwits.gallery"
    }

    sourceSets.commonTest.dependencies {
        implementation(kotlin("test"))
        implementation(libs.kotlinx.coroutines.test)
    }
    sourceSets.commonMain.dependencies {
        implementation(project(path = ":modules:features:cache-datasource"))
        implementation(project(path = ":modules:features:settings"))
        implementation(project(path = ":modules:core:communication"))
        implementation(project(path = ":modules:core:core-ui"))
        implementation(project(path = ":modules:core:async"))
        implementation(project(path = ":modules:core:image-action:core"))
        implementation(libs.compose.resources)
        implementation(libs.kotlinx.datetime)
        implementation(libs.koin.core)
    }
}
