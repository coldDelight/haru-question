package com.colddelight.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.colddelight.data.local.entity.AnswerEntity
import com.colddelight.data.local.entity.QuestionEntity

@Dao
interface AnswerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)//같은 ID Replace
    suspend fun insetAnswer(answer :AnswerEntity)

    @Query("SELECT * FROM answer WHERE id = (:id)")
    suspend fun getAnswer(id:Int): AnswerEntity

    @Query("SELECT * FROM answer")
    suspend fun getAllAnswer():List<AnswerEntity>

    @Query("DELETE FROM  answer WHERE id = :id")
    fun delAnswer(id: Int)
}