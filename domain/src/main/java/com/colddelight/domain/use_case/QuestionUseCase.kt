package com.colddelight.domain.use_case

import com.colddelight.domain.model.DomainQuestion
import com.colddelight.domain.repository.QuestionRepository



class QuestionUseCase(
    private val repository: QuestionRepository
) {
    suspend fun getQuestion(id:Int):DomainQuestion{
        return repository.getQuestion(id)
    }
    suspend fun addQuestion(question: DomainQuestion){
        repository.addQuestion(question)
    }
    suspend fun delQuestion(id: Int){
        return repository.delQuestion(id)
    }

    suspend fun getAllQuestion():List<DomainQuestion>{
        return repository.getAllQuestion()
    }

}