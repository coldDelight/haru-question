package com.colddelight.haru_question.feat_home.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.colddelight.domain.model.DomainQuestion
import com.colddelight.domain.use_case.QuestionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val UseCase: QuestionUseCase
):ViewModel(){
    data class QuestionState(
        val questionData:DomainQuestion = DomainQuestion("","","",-1),
        val isLoading: Boolean = true,
        val error: String = ""
    )

    private val _state = mutableStateOf(QuestionState())
    val state:State<QuestionState> = _state

    init{
        viewModelScope.launch(Dispatchers.IO) {
//            UseCase.delQuestion(2)
            val tes = UseCase.getAllQuestion()
//            val tes = getQuestion.invoke(2)
            Log.e("tes", "tes: $tes", )
        }
    }

//    suspend fun getQuestionS(){
//        val tes = getQuestion.invoke(1)
//        Log.e("ffff", "getQuestionS: $tes", )
//    }


}