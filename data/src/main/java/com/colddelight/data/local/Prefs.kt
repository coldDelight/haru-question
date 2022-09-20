package com.colddelight.data.local

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.colddelight.data.R

//class Prefs(private val prefs: SharedPreferences) {
    class Prefs(private val app: Application) {
    private val prefs  = app.getSharedPreferences(R.string.PREFERENCES_NAME.toString(), Context.MODE_PRIVATE)

    var lastDate:String
        get() = prefs.getString(R.string.DATE_KEY.toString(), "NO_DATE")?:"NO_DATE"
        @Synchronized
        set(date){
            prefs.edit().putString(R.string.DATE_KEY.toString(),date).apply()
        }
    var questionId:Int
        get() = prefs.getInt(R.string.QID_KEY.toString(), 1)

        @Synchronized
        set(id:Int){
            prefs.edit().putInt(R.string.QID_KEY.toString(),id).apply()
        }

    var maxNumber:Int
        get() = prefs.getInt(R.string.MAX_NUMBER_KEY.toString(), 2)

        @Synchronized
        set(value:Int){
            prefs.edit().putInt(R.string.MAX_NUMBER_KEY.toString(),value).apply()
        }
    var isChecked:Boolean
        get() = prefs.getBoolean(R.string.IS_CHECK_KEY.toString(), false)

        @Synchronized
        set(value){
            prefs.edit().putBoolean(R.string.IS_CHECK_KEY.toString(),value).apply()
        }

}