package extension.model


import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.Project

object ApkConfig {
    const val APPLICATION_ID = "com.markettwits.waifupics"

    const val MIN_SDK_VERSION = 24

    const val TARGET_SDK_VERSION = 36
    const val COMPILE_SDK_VERSION = 37

    val Project.VERSION_CODE
        get() = propOrNull("version_code")?.toInt() ?: catalogVersion("versionCode").toInt()

    val Project.VERSION_NAME
        get() = propOrNull("version_name")
            ?: catalogVersion("versionName")

    val Project.CURRENT_FLAVOR_TYPE: FlavorType
        get() {
            val default = FlavorType.DEV
            val key = "current_flavor_type"
            val propValue = propOrNull(key)
            if (propValue == null) {
                logger.warn("Property $key was not found, writing default $default")
            }
            return FlavorType.values().find { it.name == propValue } ?: default
        }
}

internal fun Project.propOrNull(key: String): String? {
    return providers.gradleProperty(key).orNull
}

private fun Project.catalogVersion(name: String): String =
    extensions.getByType(VersionCatalogsExtension::class.java)
        .named("libs")
        .findVersion(name)
        .orElseThrow { IllegalStateException("$name is missing from the libs version catalog") }
        .requiredVersion
