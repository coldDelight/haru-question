package com.colddelight.haru_question.feat_home.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.colddelight.domain.model.DomainAnswer
import com.colddelight.domain.use_case.AnswerUseCase
import com.colddelight.haru_question.HaruQuestionApp
import com.colddelight.haru_question.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class WriteViewModel @Inject constructor(
    private val UseCase: AnswerUseCase
): ViewModel() {
    init {
        viewModelScope.launch(Dispatchers.IO) {
            val test = UseCase.getAllAnswer()
            Log.e("viewModelInit", "viewModelInit 질문 답 : $test", )

        }
    }


    fun onSubmit(text:String){
        val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
        val dateNow = LocalDate.now().format(formatter)
        val q_id = HaruQuestionApp.prefs.questionId
        //TODO 임시 데이터 삭제
        val data = DomainAnswer(text,dateNow,q_id)
        viewModelScope.launch(Dispatchers.IO) {
//            UseCase.addAnswer(data).run {
//                HaruQuestionApp.prefs.lastDate = date
//                HaruQuestionApp.prefs.isChecked = false
//                if(q_id< R.string.max_question_number){
//                    HaruQuestionApp.prefs.questionId = q_id+1
//                }else{
//                    HaruQuestionApp.prefs.questionId = 1
//                }
//            }
        }
    }

}