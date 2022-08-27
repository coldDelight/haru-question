package com.colddelight.data.local

import android.content.Context

class Prefs(context: Context) {
    private val prefNm="HaruP"
    private val prefs=context.getSharedPreferences(prefNm, Context.MODE_PRIVATE)

    //스트링 리소스로 빼기
    var lastDate:String?
        get() = prefs.getString("LAST_DATE", "NO_DATE")
        set(date){
            prefs.edit().putString("LAST_DATE",date).apply()
        }
    var questionId:Int
        get() = prefs.getInt("QUESTION_ID", 1)

        set(id:Int){
            prefs.edit().putInt("QUESTION_ID",id).apply()
        }
    var isChecked:Boolean
        get() = prefs.getBoolean("IS_CHECKED", false)
        set(value){
            prefs.edit().putBoolean("IS_CHECKED",value).apply()
        }

}