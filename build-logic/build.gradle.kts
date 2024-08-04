plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
    maven("https://maven.pkg.jetbrains.space/kotlin/p/wasm/experimental")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://plugins.gradle.org/m2/")
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    implementation(libs.android.gradle)
    implementation(libs.kotlin.gradle)
    implementation("org.jetbrains.compose:org.jetbrains.compose.gradle.plugin:1.7.0-alpha02")
    implementation("org.jetbrains.kotlin.multiplatform:org.jetbrains.kotlin.multiplatform.gradle.plugin:2.0.20-RC")
    implementation("org.jetbrains.kotlin.plugin.compose:org.jetbrains.kotlin.plugin.compose.gradle.plugin:2.0.20-RC")
}