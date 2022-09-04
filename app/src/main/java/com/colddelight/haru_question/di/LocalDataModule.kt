package com.colddelight.haru_question.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.colddelight.data.local.HaruDatabase
import com.colddelight.data.local.Prefs
import com.colddelight.domain.repository.QnARepository
import com.colddelight.domain.use_case.QnAUseCase
import com.colddelight.haru_question.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
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
    fun providePreferencesDataStore(@ApplicationContext appContext: Context):DataStore<Preferences>{
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler (
                produceNewData = { emptyPreferences()}
            ),
            migrations = listOf(SharedPreferencesMigration(appContext, R.string.PREFERENCES_NAME.toString())),
            scope = CoroutineScope(Dispatchers.IO+ SupervisorJob()),
            produceFile = {appContext.preferencesDataStoreFile(R.string.PREFERENCES_NAME.toString())}
            )
    }

    @Provides
    @Singleton
    fun providePrefs(dataStore: DataStore<Preferences>):Prefs {
        return Prefs(dataStore)
    }




}


