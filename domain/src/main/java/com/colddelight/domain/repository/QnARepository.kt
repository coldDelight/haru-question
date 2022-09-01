package com.colddelight.domain.repository

import com.colddelight.domain.model.DomainQnA

interface QnARepository {
    suspend fun getQnA(id:Int) : DomainQnA
    suspend fun getAllQnA() : List<DomainQnA>
}