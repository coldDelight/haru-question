package com.colddelight.haru_question.presentation.viewmodel

import android.util.Log
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
class HaruListViewModel @Inject constructor(
    private val UseCase: QnAUseCase
): ViewModel() {
    private var _itemList: MutableLiveData<List<DomainReQnA>> = MutableLiveData(listOf())
    val itemList : MutableLiveData<List<DomainReQnA>>
        get() = _itemList
    init {
        viewModelScope.launch(Dispatchers.IO) {
            Log.e("TAG", "${UseCase.getAllQnA()}: ", )
            _itemList.postValue(UseCase.getAllQnA())
        }
    }

}