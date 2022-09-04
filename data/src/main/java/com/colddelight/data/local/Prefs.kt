package com.colddelight.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map

class Prefs(private val dataStore: DataStore<Preferences>) {

    suspend fun setValue(key:String,value:String){
        val prefKey = stringPreferencesKey(key)
        dataStore.edit {
            it[prefKey]= value
        }
    }
    suspend fun setValue(key:String,value:Int){
        val prefKey = intPreferencesKey(key)
        dataStore.edit {
            it[prefKey]= value
        }
    }
    suspend fun setValue(key:String,value:Boolean){
        val prefKey = booleanPreferencesKey(key)
        dataStore.edit {
            it[prefKey]= value
        }
    }

    fun getStringData(key:String):Flow<String>{
        val prefKey = stringPreferencesKey(key)
        val data = dataStore.data.map {
            it[prefKey]?:""
        }
        return data
    }
    fun getIntData(key:String):Flow<Int>{
        val prefKey = intPreferencesKey(key)
        val data = dataStore.data.map {
            it[prefKey]?:0
        }
        return data
    }
    fun getBooleanData(key:String):Flow<Boolean>{
        val prefKey = booleanPreferencesKey(key)
        val data = dataStore.data.map {
            it[prefKey]?:false
        }
        return data
    }

}