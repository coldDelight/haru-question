package com.colddelight.data.repository

import android.util.Log
import com.colddelight.data.local.QuestionDao
import com.colddelight.data.local.entity.QuestionEntity
import com.colddelight.domain.model.DomainQuestion
import com.colddelight.domain.repository.QuestionRepository

class QuestionRepositoryImpl(
    private val dao: QuestionDao
):QuestionRepository{
    override suspend fun getQuestion(id: Int): DomainQuestion{
        val question = dao.getQuestion(id).toDomainQuestion()
        Log.e("check impl", "getQuestion: $question", )
        return question
    }

    override suspend fun getAllQuestion(): List<DomainQuestion> {
        return dao.getAllQuestion().map { it.toDomainQuestion() }
    }

    override suspend fun addQuestion(question: DomainQuestion) {
        val teset = QuestionEntity(question.question,question.quote,question.quoteAuthor)
        dao.insetQuestion(teset)
    }
    override suspend fun delQuestion(id:Int) {
        dao.delQuestion(id)
    }

}