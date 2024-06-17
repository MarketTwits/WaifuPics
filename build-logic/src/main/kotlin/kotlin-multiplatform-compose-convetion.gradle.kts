import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    id("org.jetbrains.kotlin.plugin.compose")
    id("org.jetbrains.compose")
    id("kotlin-multiplatform-convention")
}

kotlin {
    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    compilerOptions {
        freeCompilerArgs.addAll(stabilityConfiguration())
    }
}

fun Project.stabilityConfiguration() = listOf(
    "-P",
    "plugin:androidx.compose.compiler.plugins.kotlin:stabilityConfigurationPath=${project.rootDir.absolutePath}/compose_stability_config.conf",
)

fun Project.strongSkippingConfiguration() = listOf(
    "-P",
    "plugin:androidx.compose.compiler.plugins.kotlin:experimentalStrongSkipping=true",
)

fun Project.stableTypesReportConfiguration(): List<String> {
    val composeReportsDir = "compose_reports"
    return listOf(
        "-P",
        "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=" +
                project.layout.buildDirectory.get().dir(composeReportsDir).asFile.absolutePath,
        "-P",
        "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=" +
                project.layout.buildDirectory.get().dir(composeReportsDir).asFile.absolutePath,
    )
}