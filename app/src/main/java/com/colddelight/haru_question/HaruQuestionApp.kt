package com.colddelight.haru_question

import android.app.Application
import com.colddelight.data.local.Prefs
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HaruQuestionApp:Application() {
    companion object{
        lateinit var prefs:Prefs
    }
    override fun onCreate() {
        prefs=Prefs(applicationContext)
        //TODO 테스트용 지우기
        prefs.isChecked=false
        prefs.questionId=2
        prefs.lastDate="NO_DATE"
        super.onCreate()
    }
}