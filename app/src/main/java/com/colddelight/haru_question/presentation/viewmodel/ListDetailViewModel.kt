package com.colddelight.haru_question.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.colddelight.domain.model.DomainReQnA
import com.colddelight.domain.use_case.QnAUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListDetailViewModel @Inject constructor(
    private val UseCase: QnAUseCase
): ViewModel() {
    private var _item: MutableLiveData<DomainReQnA> = MutableLiveData()
    val item : MutableLiveData<DomainReQnA>
        get() = _item

    suspend fun getQna(id:Int,a_id:Int){
        viewModelScope.launch(Dispatchers.IO) {
            _item.postValue(UseCase.getQnA(id,a_id))
        }
    }


}