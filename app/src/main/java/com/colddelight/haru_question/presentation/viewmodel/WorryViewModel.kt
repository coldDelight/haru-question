package com.colddelight.haru_question.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WorryViewModel : ViewModel() {
    //TODO 텍스트 목록 저장 방식
    private val textList = arrayListOf(
        "확실합니다.","의심할 여지가 없습니다.","무조건입니다.","믿어봐도 좋을것같습니다.","대부분 그렇습니다.",
        "네.","좋습니다.","좋아보입니다.","그대로 진행하세요.","계산결과 긍정적입니다.",
        "다음에 다시 물어 보세요.","당신에게 지금 말안하는게 좋아보입니다.","지금은 예측할수없습니다.","집중해서 다시 물어 보세요.",
        "모르겠습니다.","믿지 마세요.","제 대답은 아닌것 같습니다.","좋아 보이지 않습니다.","아니요.","굉장히 의심스럽습니다."
    )

    private var _item: MutableLiveData<String> = MutableLiveData()
    val item : MutableLiveData<String>
        get() = _item
    init {
        _item.value = "마음속으로 고민을 생각하고 위의 달을 터치하세요"
    }

    fun getText(){
        val rand = (0..19).random()
        _item.value= textList[rand]
    }
}


