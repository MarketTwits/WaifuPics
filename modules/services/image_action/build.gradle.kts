plugins {
    id("kotlin-multiplatform-convention")
}

android {
    namespace = "com.markettwits.image_action"

}

kotlin{
    sourceSets.androidMain.dependencies {
        implementation(libs.core.ktx)
        implementation(libs.appcompat)
    }
}