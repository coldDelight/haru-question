package com.colddelight.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.colddelight.domain.model.DomainAnswer

@Entity
data class AnswerEntity(
    val answer : String,
    val date : String,
    @PrimaryKey(autoGenerate = true) val id: Int=0
){
    fun toDomainAnswer(): DomainAnswer {
        return DomainAnswer(
            answer = answer,
            date = date,
            id = id
        )
    }
}