package com.colddelight.domain.repository

import com.colddelight.domain.model.DomainQuestion

interface QuestionRepository {

    suspend fun getQuestion(id:Int): DomainQuestion
    suspend fun getAllQuestion() : List<DomainQuestion>
    suspend fun addQuestion(question:DomainQuestion)
    suspend fun delQuestion(id:Int)

}