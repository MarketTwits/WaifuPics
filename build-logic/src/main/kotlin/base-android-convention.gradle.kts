import com.android.build.gradle.BaseExtension

configure<BaseExtension> {


    compileSdkVersion(libs.versions.compileSdk.get().toString().toInt())

    defaultConfig {
        minSdk =    libs.versions.minSdk.get().toString().toInt()
        targetSdk = libs.versions.targetSdk.get().toString().toInt()
        versionName = libs.versions.versionName.get().toString()
        versionCode = versionCode(versionName ?: "")
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = projectJavaVersion
        targetCompatibility = projectJavaVersion
    }
}
fun versionCode(versionName: String): Int {
    val components = versionName.split(".")

    val major = components.getOrNull(0)?.toIntOrNull()
        ?: throw IllegalArgumentException("major version in version name not found")
    val minor = components.getOrNull(1)?.toIntOrNull()
        ?: throw IllegalArgumentException("minor version in version name not found")
    val patch = components.getOrNull(2)?.toIntOrNull()
        ?: throw IllegalArgumentException("patch version in version name not found")

    return major * 10000 + minor * 100 + patch
}

