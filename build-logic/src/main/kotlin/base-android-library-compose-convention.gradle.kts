import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import localLibs
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType

configure<BaseExtension>{
    val extension = extensions.getByType<LibraryExtension>()
    extension.buildFeatures{
        compose = true
    }
    composeOptions{
        kotlinCompilerExtensionVersion  = localLibs.findVersion("compose").get().toString()
    }
}