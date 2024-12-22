import com.android.build.gradle.BaseExtension
import extension.projectJavaVersion
import extension.libs
import extension.model.ApkConfig
import extension.model.ApkConfig.VERSION_CODE
import extension.model.ApkConfig.VERSION_NAME
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

configure<BaseExtension> {

    compileSdkVersion(ApkConfig.COMPILE_SDK_VERSION)

    defaultConfig {
        minSdk = ApkConfig.MIN_SDK_VERSION
        targetSdk = ApkConfig.TARGET_SDK_VERSION
        versionName = project.VERSION_NAME
        versionCode = project.VERSION_CODE
    }

    compileOptions {
        sourceCompatibility = projectJavaVersion
        targetCompatibility = projectJavaVersion
    }

    packagingOptions.resources{
        excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }

}