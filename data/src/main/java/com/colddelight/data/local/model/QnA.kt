package com.colddelight.data.local.model

import androidx.room.Embedded
import androidx.room.Relation
import com.colddelight.data.local.entity.AnswerEntity
import com.colddelight.data.local.entity.QuestionEntity
import com.colddelight.domain.model.DomainQnA

data class QnA (
    @Embedded val question: QuestionEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "q_id"
    )
    val answer: List<AnswerEntity>?
    ) {
    fun toDomainQnA(): DomainQnA {
        return if(answer!=null){
            DomainQnA(
                question = question.question,
                date = answer.map { it.date },
                quote = question.quote,
                quoteAuthor = question.quoteAuthor,
                answer = answer.map { it.answer },
                id = question.id,
                a_id = answer.map { it.id }
            )
        }else{
            DomainQnA(
                question = question.question,
                date = arrayListOf(),
                quote = question.quote,
                quoteAuthor = question.quoteAuthor,
                answer = arrayListOf(),
                id =question.id,
                a_id = arrayListOf()

            )
        }
    }
}