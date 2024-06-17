package com.markettwits.core

import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.types.TypedRealmObject
import kotlin.reflect.KClass

interface RealmDatabaseProvider{

    fun realm(schema: Set<KClass<out TypedRealmObject>>) : Realm

    abstract class Abstract : RealmDatabaseProvider {
        protected fun schema(schema: Set<KClass<out TypedRealmObject>>) =
        RealmConfiguration.create(schema = schema)
    }

    class Base : Abstract() {
        override fun realm(schema: Set<KClass<out TypedRealmObject>>) = Realm.open(schema(schema))
    }

}
