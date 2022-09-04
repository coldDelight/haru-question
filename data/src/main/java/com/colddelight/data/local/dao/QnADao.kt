package com.colddelight.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.colddelight.data.local.model.QnA

@Dao
interface QnADao {
    @Transaction
    @Query("SELECT * FROM question WHERE question.id = :id")
    suspend fun getQnA(id:Int):QnA

    @Transaction
    @Query("SELECT * FROM question")
    suspend fun getAllQnA():List<QnA>
}