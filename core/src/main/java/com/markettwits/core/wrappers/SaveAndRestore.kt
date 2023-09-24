package com.markettwits.core.wrappers

import android.util.Log

interface SaveAndRestore {
    fun save(bundle: WrapBundle)
    fun restore(bundle: WrapBundle)
    fun init(firstRun : Boolean)
}
interface SaveAndRestoreState : SaveAndRestore{
    interface Callback{
        fun save(bundle : WrapBundle)
        fun restored(bundle: WrapBundle)
    }
    fun subscribe(callback : Callback)
    fun unsubscribe(callback : Callback)

    class Base : SaveAndRestoreState{
        private val set = mutableSetOf<Callback>()

        override fun subscribe(callback: Callback) {
            set.add(callback)
            Log.d("mt05" ,"SaveAndRestore  #subscribe callback $callback")
        }

        override fun unsubscribe(callback: Callback) {
            set.remove(callback)
        }

        override fun save(bundle: WrapBundle) {
            set.forEach { it.save(bundle) }
            Log.d("mt05" ,"SaveAndRestore  #save setSize :  ${set.size}")
        }

        override fun restore(bundle: WrapBundle) {
            Log.d("mt05", "DeathHappened : ${ProcessDeathHappened.deathHappened}")
            if (ProcessDeathHappened.deathHappened){
                ProcessDeathHappened.deathHappened = false
                set.forEach { it.restored(bundle) }
                Log.d("mt05" ,"SaveAndRestore #restore called")
                Log.d("mt05" ,"SaveAndRestore #restore count set: ${set.size}")
            }
        }

        override fun init(firstRun: Boolean) {
            if (firstRun) ProcessDeathHappened.deathHappened = false
        }
    }
}
private object ProcessDeathHappened{
    var deathHappened = true
}