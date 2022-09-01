package com.colddelight.data.local.dao

import androidx.room.*
import com.colddelight.data.local.entity.QuestionEntity

@Dao
interface QuestionDao {

    //질문 입력
    @Insert(onConflict = OnConflictStrategy.REPLACE)//같은 ID Replace
    suspend fun insetQuestion(question : QuestionEntity)


    //질문 하나 가지고 오기
    @Query("SELECT * FROM question WHERE id = :id")
    suspend fun getQuestion(id:Int):QuestionEntity

    @Query("SELECT * FROM question")
    suspend fun getAllQuestion():List<QuestionEntity>

    @Query("DELETE FROM  question WHERE id = :id")
    fun delQuestion(id: Int)


}