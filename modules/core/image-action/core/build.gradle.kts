plugins {
    id("kotlin-multiplatform-convention")
}

android {
    namespace = "com.markettwits.image_action_core"
}

kotlin{
    sourceSets.commonMain.dependencies {
        implementation(libs.koin.core)
        implementation(libs.filekit.core)
        implementation(libs.coil.mp)
    }
    sourceSets.androidMain.dependencies {
        implementation(libs.core.ktx)
        implementation(libs.appcompat)
    }
    sourceSets.jvmMain.dependencies {
        implementation("com.sun.jna:jna:3.0.9")
        implementation("net.java.dev.jna:jna-platform:4.0.0")
    }
}