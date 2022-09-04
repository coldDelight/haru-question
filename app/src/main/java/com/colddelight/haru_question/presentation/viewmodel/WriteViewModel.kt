package com.colddelight.haru_question.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.colddelight.data.R
import com.colddelight.data.local.Prefs
import com.colddelight.domain.model.DomainAnswer
import com.colddelight.domain.use_case.AnswerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class WriteViewModel @Inject constructor(
    private val UseCase: AnswerUseCase,
    private val prefs: Prefs
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
        val q_id = prefs.questionId
        //TODO 임시 데이터 삭제
        val data = DomainAnswer(text,dateNow,q_id)
        viewModelScope.launch(Dispatchers.IO) {
            UseCase.addAnswer(data).run {
                prefs.lastDate = date
                prefs.isChecked = false
                if(q_id< R.string.max_question_number){
                    prefs.questionId = q_id+1
                }else{
                    prefs.questionId = 1
                }
            }
        }
    }

}