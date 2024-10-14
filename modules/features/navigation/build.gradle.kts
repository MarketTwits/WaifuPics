plugins {
    id("kotlin-multiplatform-compose-convetion")
}

android {
    namespace = "com.markettwits.navigation"
}

kotlin{
    sourceSets.commonMain.dependencies {
        implementation(project(":modules:core:core-ui"))
        implementation(project(":modules:core:async"))
        implementation(project(":modules:features:cache-datasource"))
        implementation(compose.components.resources)
        implementation(libs.koin.core)
    }

}