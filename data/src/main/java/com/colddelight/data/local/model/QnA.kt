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
    val answer: AnswerEntity?
    ) {
    fun toDomainQnA(): DomainQnA {
        return if(answer!=null){
            DomainQnA(
                question = question.question,
                date = answer.date,
                quote = question.quote,
                quoteAuthor = question.quoteAuthor,
                answer = answer.answer,
                id = question.id,
            )
        }else{
            DomainQnA(
                question = question.question,
                date = "NO_DATE",
                quote = question.quote,
                quoteAuthor = question.quoteAuthor,
                answer = "NO_ANSWER",
                id=question.id
            )
        }
    }
}