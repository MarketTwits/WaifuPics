plugins {
    id("kotlin-multiplatform-compose-convetion")
}

android {
    namespace = "com.markettwits.root"

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
kotlin{
    sourceSets.commonMain.dependencies {
        implementation(libs.koin.core)
        implementation(libs.koin.compose)
        implementation("org.jetbrains.androidx.navigation:navigation-compose:2.7.0-alpha07")
        implementation(project(path = ":modules:features:random_image"))
        implementation(project(path = ":modules:services:core-ui"))
        implementation(project(path = ":modules:services:core"))
        implementation(project(path = ":modules:features:navigation"))
        implementation(project(path = ":modules:features:gallery"))
        implementation(project(path = ":modules:features:about"))
    }
}