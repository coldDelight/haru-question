package com.colddelight.haru_question.di

import android.app.Application
import androidx.room.Room
import com.colddelight.data.local.HaruDatabase
import com.colddelight.data.repository.AnswerRepositoryImpl
import com.colddelight.data.repository.QnARepositoryImpl
import com.colddelight.data.repository.QuestionRepositoryImpl
import com.colddelight.domain.repository.AnswerRepository
import com.colddelight.domain.repository.QnARepository
import com.colddelight.domain.repository.QuestionRepository
import com.colddelight.domain.use_case.AnswerUseCase
import com.colddelight.domain.use_case.QnAUseCase
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
    fun provideAnswerUseCase(repository: AnswerRepository):AnswerUseCase{
        return AnswerUseCase(repository)
    }
    @Provides
    @Singleton
    fun provideQnAUseCase(repository: QnARepository):QnAUseCase{
        return QnAUseCase(repository)
    }
    @Provides
    @Singleton
    fun provideQuestionRepository(
        db:HaruDatabase,
    ):QuestionRepository{
        return QuestionRepositoryImpl(db.questionDao)
    }
    @Provides
    @Singleton
    fun provideAnswerRepository(
        db:HaruDatabase,
    ):AnswerRepository{
        return AnswerRepositoryImpl(db.answerDao)
    }
    @Provides
    @Singleton
    fun provideQnARepository(
        db:HaruDatabase,
    ):QnARepository{
        return QnARepositoryImpl(db.qnADao)
    }
    @Provides
    @Singleton
    fun provideHaruDatabase(app:Application):HaruDatabase{
        return Room.databaseBuilder(
            app,HaruDatabase::class.java,"haru_db"
        ).fallbackToDestructiveMigration().
        build()
    }

}