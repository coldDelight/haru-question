package com.colddelight.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.colddelight.domain.model.DomainQuestion

@Entity
data class QuestionEntity (
    val question : String,
    val quote : String,
    val quoteAuthor : String,
    @PrimaryKey(autoGenerate = true) val id: Int=0
){
    fun toDomainQuestion():DomainQuestion {
        return DomainQuestion(
            question = question,
            quote = quote,
            quoteAuthor = quoteAuthor,
            id = id
        )
    }
}