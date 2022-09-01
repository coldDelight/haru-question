package com.colddelight.domain.use_case

import com.colddelight.domain.model.DomainQnA
import com.colddelight.domain.model.DomainQuestion
import com.colddelight.domain.repository.QnARepository

class QnAUseCase(private val repository: QnARepository) {
    suspend fun getQnA(id:Int):DomainQnA{
        return repository.getQnA(id)
    }
    suspend fun getAllQnA():List<DomainQnA>{
        return repository.getAllQnA()
    }
}