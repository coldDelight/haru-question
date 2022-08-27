package com.colddelight.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.colddelight.data.local.entity.AnswerEntity

@Dao
interface AnswerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)//같은 ID Replace
    suspend fun insetAnswer(answer :AnswerEntity)

    //질문 하나 가지고 오기
    @Query("SELECT * FROM AnswerEntity WHERE id = (:id)")
    suspend fun getAnswer(id:Int): AnswerEntity
}