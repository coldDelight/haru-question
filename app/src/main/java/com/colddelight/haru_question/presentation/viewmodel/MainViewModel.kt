package com.colddelight.haru_question.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.colddelight.data.local.Prefs
import com.colddelight.domain.model.DomainQuestion
import com.colddelight.domain.use_case.QuestionUseCase
import com.colddelight.haru_question.HaruQuestionApp
import com.colddelight.haru_question.R
import com.colddelight.haru_question.core.util.HaruState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val UseCase: QuestionUseCase,
    prefs: Prefs
):ViewModel(){
    val test = "f"
    data class QuestionState(
        val questionData: DomainQuestion = DomainQuestion("","",""),
        val state: HaruState = HaruState.READY,
        val error: String = ""
    )
    private val _state = MutableStateFlow(QuestionState())
    val state: StateFlow<QuestionState> = _state.asStateFlow()

    private lateinit var lastDate:String
    private var questionId:Int = 0
    private var isChecked:Boolean = false

    init{
        viewModelScope.launch(Dispatchers.IO)  {

            prefs.setValue(R.string.DATE_KEY.toString(),"NO_DATE")
            prefs.setValue(R.string.IS_CHECK_KEY.toString(),false)
            prefs.setValue(R.string.QID_KEY.toString(),1)
            val lastDate=  prefs.getStringData(R.string.DATE_KEY.toString()).last()
            val questionId=  prefs.getIntData(R.string.QID_KEY.toString()).last()
            val isChecked=  prefs.getBooleanData(R.string.IS_CHECK_KEY.toString()).last()
            Log.e("TAG", "$lastDate: ", )
            setHomeTitle(lastDate,isChecked)
//            UseCase.addQuestion(DomainQuestion("살아간다는 것은 무엇인가요","그래, 산다는 것은 바람이 약해지는 것을 기다리는게 아니라\\n그 바람 속을 헤쳐나가는 것이다.","바람속을 걷는법"))
//            UseCase.addQuestion(DomainQuestion("최근에 새로 시작한게 있나요? 있다면 무엇인가요","험한 언덕을 오르기 위해서는 처음에는 천천히 걷는 것이 필요하다.","William Shakespeare"))
//            UseCase.addQuestion(DomainQuestion("좋아하는 영화에 대하여 알려주세요","영화란 지루한 부분이 커트된 인생이다.","Alfred Hitchcock"))
//            UseCase.addQuestion(DomainQuestion("지금 생각나는 친구에 대하여 알려주세요","고난과 불행이 찾아올 때, 비로소 친구가 친구임을 안다.","이태백"))
//            UseCase.addQuestion(DomainQuestion("실패가 두려워 도전하지 못하고 고민중인게 있나요?","인생을 다시 산다면 다음번에는 더 많은 실수를 저지르리라","Nadin Stair"))
//            UseCase.addQuestion(DomainQuestion("내일은 무서운일이 기다리고 있습니까?","두려움을 용기로 바꾸어 사용해라","kim chenc"))
////            UseCase.delQuestion(2)
//            val tes = UseCase.getAllQuestion()
//            Log.e("MainViewModel", "전체 질문리스트: $tes", )
        }


    }


    private fun setHomeTitle(lastDate:String, isChecked:Boolean){
        val dateNow = LocalDate.now()
        if(dateNow.equals(lastDate)){
            _state.value = QuestionState(
                state= HaruState.WAIT
            )
        }else if(isChecked){
            setQuestion()
        }else{
            _state.value = QuestionState(
                state= HaruState.READY
            )
        }
    }

    fun checkQuestion(){
        isChecked=true
        setQuestion()
    }

    private fun setQuestion(){
        questionId
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = QuestionState(
                state = HaruState.SHOW,
                questionData = UseCase.getQuestion(questionId)
            )
        }
    }

}