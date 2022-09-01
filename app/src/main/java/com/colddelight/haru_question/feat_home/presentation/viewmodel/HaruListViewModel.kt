package com.colddelight.haru_question.feat_home.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.colddelight.domain.model.DomainQnA
import com.colddelight.domain.use_case.QnAUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HaruListViewModel @Inject constructor(
    private val UseCase: QnAUseCase
): ViewModel() {
    private var _itemList: MutableLiveData<List<DomainQnA>> = MutableLiveData(listOf())
    val itemList : MutableLiveData<List<DomainQnA>>
        get() = _itemList
    init {
        viewModelScope.launch(Dispatchers.IO) {
            _itemList.postValue(UseCase.getAllQnA())
        }
    }
    fun check(){
        viewModelScope.launch(Dispatchers.IO) {
            UseCase.getAllQnA()
        }
    }

}