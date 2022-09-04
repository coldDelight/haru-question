package com.colddelight.haru_question.presentation.viewmodel

import android.util.Log
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
        val questionData:DomainQuestion = DomainQuestion("","",""),
        val state: HaruState = HaruState.READY,
        val error: String = ""
    )
    private val _state = MutableStateFlow(QuestionState())
    val state:StateFlow<QuestionState> = _state.asStateFlow()

    init{
        viewModelScope.launch(Dispatchers.IO) {
//            UseCase.addQuestion(DomainQuestion("살아간다는 것은 무엇인가요","그래, 산다는 것은 바람이 약해지는 것을 기다리는게 아니라\\n그 바람 속을 헤쳐나가는 것이다.","바람속을 걷는법"))
//            UseCase.addQuestion(DomainQuestion("최근에 새로 시작한게 있나요? 있다면 무엇인가요","험한 언덕을 오르기 위해서는 처음에는 천천히 걷는 것이 필요하다.","William Shakespeare"))
//            UseCase.addQuestion(DomainQuestion("좋아하는 영화에 대하여 알려주세요","영화란 지루한 부분이 커트된 인생이다.","Alfred Hitchcock"))
//            UseCase.addQuestion(DomainQuestion("지금 생각나는 친구에 대하여 알려주세요","고난과 불행이 찾아올 때, 비로소 친구가 친구임을 안다.","이태백"))
//            UseCase.addQuestion(DomainQuestion("실패가 두려워 도전하지 못하고 고민중인게 있나요?","인생을 다시 산다면 다음번에는 더 많은 실수를 저지르리라","Nadin Stair"))
//            UseCase.addQuestion(DomainQuestion("내일은 무서운일이 기다리고 있습니까?","두려움을 용기로 바꾸어 사용해라","kim chenc",-1))
////            UseCase.delQuestion(2)
            val tes = UseCase.getAllQuestion()
            Log.e("viewModelInit", "viewModelInit: $tes", )
//            Log.e("dd", "date: ${HaruQuestionApp.prefs.isChecked}", )
        }
    }



//    suspend fun getQuestionS(){
//        val tes = getQuestion.invoke(1)
//        Log.e("ffff", "getQuestionS: $tes", )
//    }

}