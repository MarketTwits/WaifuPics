plugins {
    id("kotlin-multiplatform-convention")
}

android {
    namespace = "com.markettwits.waifupics.storage"
}
kotlin{
    sourceSets.commonMain.dependencies {
        implementation(libs.koin.core)
    }
}