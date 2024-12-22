package extension.model

/**
 * This enum is used to define new kotlin-generated BuildKonfig
 */
enum class FlavorType(
    val isLogEnabled: Boolean,
) {
    DEV(
        isLogEnabled = true,
    ),
    PROD(
        isLogEnabled = false,
    )
}
