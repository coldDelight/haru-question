package com.colddelight.haru_question.di

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
object UseCaseModule {
    @Provides
    @Singleton
    fun provideQuestionUseCase(repository: QuestionRepository): QuestionUseCase {
        return QuestionUseCase(repository)
    }
    @Provides
    @Singleton
    fun provideAnswerUseCase(repository: AnswerRepository): AnswerUseCase {
        return AnswerUseCase(repository)
    }
    @Provides
    @Singleton
    fun provideQnAUseCase(repository: QnARepository): QnAUseCase {
        return QnAUseCase(repository)
    }
}