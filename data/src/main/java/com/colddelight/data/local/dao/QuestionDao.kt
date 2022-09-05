package com.colddelight.data.local.dao

import androidx.room.*
import com.colddelight.data.local.entity.QuestionEntity

@Dao
interface QuestionDao {

    @Query("SELECT * FROM question WHERE id = :id")
    suspend fun getQuestion(id:Int):QuestionEntity

    @Query("SELECT * FROM question")
    suspend fun getAllQuestion():List<QuestionEntity>


}