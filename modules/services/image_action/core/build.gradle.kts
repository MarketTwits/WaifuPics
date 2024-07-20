plugins {
    id("kotlin-multiplatform-convention")
}

android {
    namespace = "com.markettwits.image_action_core"
}

kotlin{
    sourceSets.commonMain.dependencies {
        implementation(libs.koin.core)
        implementation(libs.coil.mp)
       // implementation(libs.coil.network.ktor)
    }
    sourceSets.androidMain.dependencies {
      //  implementation(libs.coil.network.okkhttp)
        implementation(libs.core.ktx)
        implementation(libs.appcompat)
    }
}