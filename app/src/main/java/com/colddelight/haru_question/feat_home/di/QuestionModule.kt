package com.colddelight.haru_question.feat_home.di

import android.app.Application
import androidx.room.Room
import com.colddelight.data.local.HaruDatabase
import com.colddelight.data.local.QuestionDao
import com.colddelight.data.repository.QuestionRepositoryImpl
import com.colddelight.domain.repository.QuestionRepository
import com.colddelight.domain.use_case.AddQuestion
import com.colddelight.domain.use_case.GetQuestion
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
    fun provideGetQuestionUseCase(repository: QuestionRepository):GetQuestion{
        return GetQuestion(repository)
    }

    @Provides
    @Singleton
    fun provideAddQuestionUseCase(repository: QuestionRepository):AddQuestion{
        return AddQuestion(repository)
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