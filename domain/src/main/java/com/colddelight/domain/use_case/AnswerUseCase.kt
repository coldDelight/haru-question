package com.colddelight.domain.use_case

import com.colddelight.domain.model.DomainAnswer
import com.colddelight.domain.repository.AnswerRepository

class AnswerUseCase(private val repository: AnswerRepository) {

    suspend fun getAnswer(id:Int): DomainAnswer {
        return repository.getAnswer(id)
    }
    suspend fun addAnswer(answer: DomainAnswer):DomainAnswer{
        repository.addAnswer(answer)
        return answer
    }
    suspend fun delAnswer(id: Int){
        return repository.delAnswer(id)
    }
    suspend fun getAllAnswer():List<DomainAnswer>{
        return repository.getAllAnswer()
    }
}