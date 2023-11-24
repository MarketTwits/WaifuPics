import com.android.build.gradle.BaseExtension
import org.gradle.kotlin.dsl.configure

configure<BaseExtension> {

    compileSdkVersion(localLibs.findVersion("compileSdk").get().toString().toInt())
    defaultConfig {
        minSdk = localLibs.findVersion("minSdk").get().toString().toInt()
        targetSdk = localLibs.findVersion("targetSdk").get().toString().toInt()
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }
    buildTypes {

        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
                "proguard-rules.pro"
            )
        }
    }
}
