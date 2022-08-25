package com.colddelight.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.colddelight.data.local.entity.QuestionEntity

@Dao
interface QuestionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)//같은 ID Replace
    suspend fun insetQuestion(question : QuestionEntity)


    //질문 하나 가지고 오기
    @Query("SELECT * FROM QuestionEntity WHERE id = :id")
    suspend fun getQuestion(id:Int):QuestionEntity

}