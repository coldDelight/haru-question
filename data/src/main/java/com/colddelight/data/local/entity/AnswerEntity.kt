package com.colddelight.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.colddelight.domain.model.DomainAnswer

@Entity (tableName = "answer",
    foreignKeys = [
        ForeignKey(
            entity = QuestionEntity::class,
            parentColumns = ["id"],
            childColumns =  ["q_id"]
        )
    ])
data class AnswerEntity(
    @ColumnInfo(name= "answer")val answer : String,
    @ColumnInfo(name= "date")val date : String,
    @ColumnInfo(name= "q_id")val q_id : Int,
    @PrimaryKey(autoGenerate = true) val id: Int=0,

    ){
    fun toDomainAnswer(): DomainAnswer {
        return DomainAnswer(
            answer = answer,
            date = date,
            id = id,
            q_id=q_id
        )
    }
}