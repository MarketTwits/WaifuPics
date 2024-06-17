
plugins {
   // id("kotlin-multiplatform-convention")
    id("kotlin-jvm-convention")

    alias(libs.plugins.realm)
}

//android {
//    namespace = "com.markettwits.cache_datasource"
//
//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_17
//        targetCompatibility = JavaVersion.VERSION_17
//    }
//}
kotlin{

    dependencies {
        implementation(libs.realm)
        implementation(libs.kotlinx.coroutines.core)
      //  implementation(libs.core.ktx)
    }

//    sourceSets.androidMain.dependencies {
//        implementation(project(":modules:services:core"))
//        implementation(libs.realm)
//        implementation(libs.core.ktx)
//        implementation(libs.compose.coil)
//    }
}
