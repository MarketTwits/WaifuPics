package com.markettwits.core.di.kmp

/**
 * [Lateinit] is used for components which can't be initialized internally
 * For example: Velocity @inject properties, Android context or spigot plugin instance
 *
 * It can't be initialized twice and can't be accessed until initialization
 */
class Lateinit<T : Any>(private val isMultipleInitializationAllowed: Boolean) : Dependency<T> {
    constructor() : this(false)

    private var instance: T? = null

    fun initialize(value: T) {
        if (instance != null && !isMultipleInitializationAllowed) {
            error("Value already initialized")
        }
        this.instance = value
    }

    fun initialize(factory: Factory<T>) {
        val value = factory.create()
        initialize(value)
    }

    override val value: T
        get() = instance ?: error("Value is not initialized yet")
}
