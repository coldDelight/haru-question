package com.colddelight.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.colddelight.data.local.dao.AnswerDao
import com.colddelight.data.local.dao.QuestionDao
import com.colddelight.data.local.entity.AnswerEntity
import com.colddelight.data.local.entity.QuestionEntity

@Database(
    entities = [QuestionEntity::class,AnswerEntity::class],
    version = 1
)
abstract  class HaruDatabase:RoomDatabase() {
    abstract val questionDao : QuestionDao
    abstract val answerDao : AnswerDao
}