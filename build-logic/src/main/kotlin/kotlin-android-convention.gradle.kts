import com.android.build.gradle.BaseExtension
import extension.projectJavaVersion
import extension.libs

configure<BaseExtension> {


    compileSdkVersion(libs.versions.compileSdk.get().toString().toInt())

    defaultConfig {
        minSdk =    libs.versions.minSdk.get().toString().toInt()
        targetSdk = libs.versions.targetSdk.get().toString().toInt()
        versionName = libs.versions.versionName.get().toString()
        versionCode = extension.versionCode(versionName ?: "")
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }



    compileOptions {
        sourceCompatibility = projectJavaVersion
        targetCompatibility = projectJavaVersion
    }

    packagingOptions.resources{
        excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
}

