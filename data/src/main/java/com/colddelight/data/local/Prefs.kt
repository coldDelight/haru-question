package com.colddelight.data.local

import android.content.SharedPreferences
import com.colddelight.data.R

class Prefs(private val prefs: SharedPreferences) {
    var lastDate:String
        get() = prefs.getString(R.string.DATE_KEY.toString(), "NO_DATE")?:"NO_DATE"
        set(date){
            prefs.edit().putString("LAST_DATE",date).apply()
        }
    var questionId:Int
        get() = prefs.getInt(R.string.QID_KEY.toString(), 1)

        set(id:Int){
            prefs.edit().putInt(R.string.QID_KEY.toString(),id).apply()
        }

    var maxNumber:Int
        get() = prefs.getInt(R.string.MAX_NUMBER_KEY.toString(), 2)
        set(value:Int){
            prefs.edit().putInt(R.string.MAX_NUMBER_KEY.toString(),value).apply()
        }
    var isChecked:Boolean
        get() = prefs.getBoolean(R.string.IS_CHECK_KEY.toString(), false)
        set(value){
            prefs.edit().putBoolean(R.string.IS_CHECK_KEY.toString(),value).apply()
        }

}