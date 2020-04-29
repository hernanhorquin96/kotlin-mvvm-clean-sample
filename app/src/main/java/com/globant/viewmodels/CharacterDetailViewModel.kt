package com.globant.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.globant.domain.entities.MarvelCharacter
import com.globant.domain.usecases.GetCharacterByIdUseCase
import com.globant.domain.utils.Result
import com.globant.utils.Data
import com.globant.utils.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharacterDetailViewModel(val getCharacterByIdUseCase: GetCharacterByIdUseCase) : ViewModel() {

    private var mutableMainState: MutableLiveData<Data<MarvelCharacter>> = MutableLiveData()
    val mainState: LiveData<Data<MarvelCharacter>>
        get() {
            return mutableMainState
        }

    fun getCharacterById(id: Int) = viewModelScope.launch {
        mutableMainState.postValue(Data(responseType = Status.LOADING))
        when (val result = withContext(Dispatchers.IO) { getCharacterByIdUseCase.invoke(id, true) }) {
            is Result.Failure -> {
                mutableMainState.postValue(Data(responseType = Status.GET_CHARACTER_BY_ID_ERROR, error = result.exception))
            }
            is Result.Success -> {
                mutableMainState.postValue(Data(responseType = Status.GET_CHARACTER_BY_ID_SUCCESS, data = result.data))
            }
        }

    }
}