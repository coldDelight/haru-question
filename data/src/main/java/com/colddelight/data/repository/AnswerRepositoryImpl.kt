package com.colddelight.data.repository

import com.colddelight.data.local.dao.AnswerDao
import com.colddelight.data.local.entity.AnswerEntity
import com.colddelight.domain.model.DomainAnswer
import com.colddelight.domain.repository.AnswerRepository
class AnswerRepositoryImpl(
    private val dao: AnswerDao
): AnswerRepository {
    override suspend fun addAnswer(question: DomainAnswer) {
        val data = AnswerEntity(question.answer,question.date,question.q_id )
        dao.insetAnswer(data)
    }
    override suspend fun delAnswer() {
        dao.delAnswer()
    }
    override suspend fun getAnswer(id: Int): DomainAnswer {
        return dao.getAnswer(id).toDomainAnswer()
    }
    override suspend fun getAllAnswer(): List<DomainAnswer> {
        return dao.getAllAnswer().map { it.toDomainAnswer() }
    }


}