package com.colddelight.domain.use_case

import com.colddelight.domain.model.DomainQuestion
import com.colddelight.domain.repository.QuestionRepository

class GetQuestion(
    private val repository: QuestionRepository
) {
    suspend operator fun invoke(id:Int): DomainQuestion {
//        return repository.getQuestion(id)
        return repository.getQuestion(id)
    }
}