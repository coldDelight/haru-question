package com.colddelight.domain.repository

import com.colddelight.domain.model.DomainAnswer

interface AnswerRepository {
    suspend fun getAnswer(id:Int): DomainAnswer
    suspend fun getAllAnswer() : List<DomainAnswer>
    suspend fun addAnswer(question: DomainAnswer)
    suspend fun delAnswer()
}