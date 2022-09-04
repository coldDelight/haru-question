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
    fun provideHaruDatabase(app:Application):HaruDatabase{
        return Room.databaseBuilder(
            app,HaruDatabase::class.java,"haru_db"
        ).fallbackToDestructiveMigration().
        build()
    }


    @Provides
    @Singleton
    fun providePrefs(prefs: SharedPreferences):Prefs {
        return Prefs(prefs)
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext appContext: Context): SharedPreferences{
        return appContext.getSharedPreferences(R.string.PREFERENCES_NAME.toString(), Context.MODE_PRIVATE)
    }




}


