package com.colddelight.haru_question.feat_home.di

import android.app.Application
import androidx.room.Room
import com.colddelight.data.local.HaruDatabase
import com.colddelight.data.repository.QuestionRepositoryImpl
import com.colddelight.domain.repository.QuestionRepository
import com.colddelight.domain.use_case.QuestionUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object QuestionModule {
    @Provides
    @Singleton
    fun provideQuestionUseCase(repository: QuestionRepository):QuestionUseCase{
        return QuestionUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideQuestionRepository(
        db:HaruDatabase,
    ):QuestionRepository{
        return QuestionRepositoryImpl(db.dao)
    }

    @Provides
    @Singleton
    fun provideHaruDatabase(app:Application):HaruDatabase{
        return Room.databaseBuilder(
            app,HaruDatabase::class.java,"haru_db"
        ).build()
    }

}