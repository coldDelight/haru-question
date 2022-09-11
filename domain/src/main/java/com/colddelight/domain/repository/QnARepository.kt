package com.colddelight.domain.repository

import com.colddelight.domain.model.DomainQnA
import com.colddelight.domain.model.DomainReQnA

interface QnARepository {
    suspend fun getQnA(id:Int,a_id:Int) : DomainReQnA
    suspend fun getAllQnA() : List<DomainReQnA>
}