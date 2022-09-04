package com.colddelight.data.repository

import android.util.Log
import com.colddelight.data.local.dao.QnADao
import com.colddelight.domain.model.DomainQnA
import com.colddelight.domain.model.DomainQuestion
import com.colddelight.domain.repository.QnARepository

class QnARepositoryImpl(private val dao: QnADao
): QnARepository {
    override suspend fun getQnA(id: Int): DomainQnA {
        return dao.getQnA(id).toDomainQnA()
    }

    override suspend fun getAllQnA(): List<DomainQnA> {
        return dao.getAllQnA().map { it.toDomainQnA() }.filter { it.date!="NO_DATE" }
    }
}