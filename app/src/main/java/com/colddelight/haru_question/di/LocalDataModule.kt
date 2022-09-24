package com.colddelight.haru_question.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.colddelight.data.local.HaruDatabase
import com.colddelight.data.local.Prefs
import com.colddelight.data.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {
    @Provides
    @Singleton
    fun provideHaruDatabase(@ApplicationContext appContext: Context): HaruDatabase {
        return Room.databaseBuilder(
            appContext,HaruDatabase::class.java,"haru_db"
        ).createFromAsset("database/haru_db.db").
//        fallbackToDestructiveMigration().
        build()
    }


    @Provides
    @Singleton
    fun providePrefs(application: Application):Prefs {
        return Prefs(application)
    }

}


