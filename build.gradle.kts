// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.kotlin.kmp.compose) apply false
    alias(libs.plugins.kotlin.kmp) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
    alias(libs.plugins.org.jetbrains.kotlin.jvm) apply false
}


tasks.withType<Wrapper>().configureEach {
    distributionType = Wrapper.DistributionType.BIN
    gradleVersion = libs.versions.gradle.get()
}
