import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.libsDirectory

plugins {
    id("com.android.library")
    id("base-android-convention")
    id("kotlin-android")
    id("base-kotlin-convention")
    id("base-android-library-compose-convention")
}
