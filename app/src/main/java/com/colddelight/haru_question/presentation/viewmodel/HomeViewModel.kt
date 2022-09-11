package com.colddelight.haru_question.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.colddelight.domain.use_case.QuestionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val UseCase: QuestionUseCase
):ViewModel(){


}