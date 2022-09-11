package com.colddelight.domain.use_case

import com.colddelight.domain.model.DomainQnA
import com.colddelight.domain.model.DomainQuestion
import com.colddelight.domain.model.DomainReQnA
import com.colddelight.domain.repository.QnARepository

class QnAUseCase(private val repository: QnARepository) {
    suspend fun getQnA(id:Int,a_id:Int):DomainReQnA{
        return repository.getQnA(id,a_id)
    }
    suspend fun getAllQnA():List<DomainReQnA>{
        return repository.getAllQnA()
    }
}