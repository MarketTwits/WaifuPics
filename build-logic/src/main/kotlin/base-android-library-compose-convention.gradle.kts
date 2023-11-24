import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.libsDirectory

configure<BaseExtension>{
    val extension = extensions.getByType<LibraryExtension>()
    extension.buildFeatures{
        compose = true
    }
    composeOptions{
        kotlinCompilerExtensionVersion  = localLibs.findVersion("compose").get().toString()
    }
}