package com.colddelight.haru_question.di

import com.colddelight.data.local.HaruDatabase
import com.colddelight.data.repository.AnswerRepositoryImpl
import com.colddelight.data.repository.QnARepositoryImpl
import com.colddelight.data.repository.QuestionRepositoryImpl
import com.colddelight.domain.repository.AnswerRepository
import com.colddelight.domain.repository.QnARepository
import com.colddelight.domain.repository.QuestionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideQuestionRepository(
        db: HaruDatabase,
    ): QuestionRepository {
        return QuestionRepositoryImpl(db.questionDao)
    }
    @Provides
    @Singleton
    fun provideAnswerRepository(
        db: HaruDatabase,
    ): AnswerRepository {
        return AnswerRepositoryImpl(db.answerDao)
    }
    @Provides
    @Singleton
    fun provideQnARepository(
        db: HaruDatabase,
    ): QnARepository {
        return QnARepositoryImpl(db.qnADao)
    }
}