package com.markettwits.core.wrappers

interface HandleDeath {
    fun firstOpening()
    fun didDeathHappen(): Boolean
    fun deathHandled()

    class Base : HandleDeath {
        private var deathHappened = true
        override fun firstOpening() {
            deathHappened = false
        }

        override fun didDeathHappen() = deathHappened
        override fun deathHandled() {
            deathHappened = false
        }
    }
}