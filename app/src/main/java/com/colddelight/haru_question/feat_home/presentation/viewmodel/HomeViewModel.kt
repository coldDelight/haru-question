package com.colddelight.haru_question.feat_home.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.colddelight.domain.model.DomainQuestion
import com.colddelight.domain.use_case.QuestionUseCase
import com.colddelight.haru_question.HaruQuestionApp
import com.colddelight.haru_question.core.util.HaruState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val UseCase: QuestionUseCase
):ViewModel(){
    data class QuestionState(
        val questionData:DomainQuestion = DomainQuestion("","","",-1),
        val state: HaruState = HaruState.READY,
        val error: String = ""
    )
    private val _state = MutableStateFlow(QuestionState())
    val state:StateFlow<QuestionState> = _state.asStateFlow()

    init{
        setHomeTitle()
        viewModelScope.launch(Dispatchers.IO) {
//            UseCase.addQuestion(DomainQuestion("오늘의 점심을 기대합니까??","밥은 그대로 옳다","김찬희",-1))
//            UseCase.addQuestion(DomainQuestion("내일은 무서운일이 기다리고 있습니까?","두려움을 용기로 바꾸어 사용해라","kim chenc",-1))
//
////            UseCase.delQuestion(2)
            val tes = UseCase.getAllQuestion()
            Log.e("tes", "tes: $tes", )
//            Log.e("dd", "date: ${HaruQuestionApp.prefs.lastDate}", )
//            Log.e("dd", "date: ${HaruQuestionApp.prefs.questionId}", )
//            Log.e("dd", "date: ${HaruQuestionApp.prefs.isChecked}", )
        }
    }

    private fun setHomeTitle(){
        val dateNow = LocalDate.now()
        val lastDate= HaruQuestionApp.prefs.lastDate
        val isChecked= HaruQuestionApp.prefs.isChecked
        if(dateNow.equals(lastDate)){
            _state.value = QuestionState(
                state=HaruState.WAIT
            )
        }else if(isChecked){
             setQuestion()
        }else{
            _state.value = QuestionState(
                state=HaruState.READY
            )
        }
    }

    fun checkQuestion(){
        HaruQuestionApp.prefs.isChecked=true
        setQuestion()
    }

    fun setQuestion(){
        val questionId= HaruQuestionApp.prefs.questionId
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = QuestionState(
                state = HaruState.SHOW,
                questionData = UseCase.getQuestion(questionId)
            )
        }
    }
//    suspend fun getQuestionS(){
//        val tes = getQuestion.invoke(1)
//        Log.e("ffff", "getQuestionS: $tes", )
//    }

}