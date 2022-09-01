package com.colddelight.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.colddelight.domain.model.DomainQuestion

@Entity (tableName = "question")
data class QuestionEntity (
    @ColumnInfo(name= "question")val question : String,
    @ColumnInfo(name= "quote")val quote : String,
    @ColumnInfo(name= "quoteAuthor")val quoteAuthor : String,
    @PrimaryKey(autoGenerate = true) val id: Int=0,

    ){
    fun toDomainQuestion():DomainQuestion {
        return DomainQuestion(
            question = question,
            quote = quote,
            quoteAuthor = quoteAuthor,
            id = id,
        )
    }
}