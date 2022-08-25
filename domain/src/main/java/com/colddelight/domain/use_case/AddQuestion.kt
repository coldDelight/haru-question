package com.colddelight.domain.use_case

import com.colddelight.domain.model.DomainQuestion
import com.colddelight.domain.repository.QuestionRepository

class AddQuestion(
    private val repository: QuestionRepository
) {
    suspend operator fun invoke(question: DomainQuestion) {
        return repository.addQuestion(question)
    }
}