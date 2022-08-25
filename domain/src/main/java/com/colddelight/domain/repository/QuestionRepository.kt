package com.colddelight.domain.repository

import com.colddelight.domain.model.DomainQuestion

interface QuestionRepository {

    suspend fun getQuestion(id:Int): DomainQuestion
    suspend fun addQuestion(question:DomainQuestion)

}