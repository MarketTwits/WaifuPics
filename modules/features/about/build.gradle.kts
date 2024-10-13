plugins {
    id("kotlin-multiplatform-compose-convetion")
}

android {
    namespace = "com.markettwits.about"

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
kotlin{
    sourceSets.commonMain.dependencies {
        implementation(project(":modules:features:core-ui"))
        implementation(compose.components.resources)
    }
}