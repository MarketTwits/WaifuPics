plugins {
    id("kotlin-multiplatform-compose-convetion")
}

android {
    namespace = "com.markettwits.navigation"
}

kotlin{
    sourceSets.commonMain.dependencies {
        implementation(project(":modules:features:core-ui"))
        implementation(project(":modules:features:async"))
        implementation(project(":modules:features:cache-datasource"))
        implementation(compose.components.resources)
        implementation(libs.koin.core)
    }

}