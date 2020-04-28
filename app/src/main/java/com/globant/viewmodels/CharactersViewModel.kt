package com.globant.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.globant.domain.entities.MarvelCharacter
import com.globant.domain.usecases.GetCharacterUseCase
import com.globant.domain.usecases.GetLocalCharactersUseCase
import com.globant.domain.utils.Result
import com.globant.utils.Data
import com.globant.utils.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharactersViewModel(val getCharactersUseCase: GetCharacterUseCase, val getLocalCharactersUseCase: GetLocalCharactersUseCase) : ViewModel() {

    private var mutableMainState: MutableLiveData<Data<List<MarvelCharacter>>> = MutableLiveData()
    val mainState: LiveData<Data<List<MarvelCharacter>>>
        get() {
            return mutableMainState
        }

    private var mutableLocalDataState: MutableLiveData<Data<List<MarvelCharacter>>> = MutableLiveData()
    val localDataState: LiveData<Data<List<MarvelCharacter>>>
        get() {
            return mutableLocalDataState
        }

    fun getAllCharacters() = viewModelScope.launch {
        mutableMainState.postValue(Data(responseType = Status.LOADING))
        withContext(Dispatchers.IO) { getCharactersUseCase.invoke() }.let { result ->
            when (result) {
                is Result.Failure -> {
                    mutableMainState.postValue(Data(responseType = Status.GET_CHARACTER_ERROR, error = result.exception))
                }
                is Result.Success -> {
                    mutableMainState.postValue(Data(responseType = Status.GET_CHARACTER_SUCCESS, data = result.data))
                }
            }
        }
    }

    fun getLocalCharacters() = viewModelScope.launch {
        mutableLocalDataState.postValue(Data(responseType = Status.LOADING))
        withContext(Dispatchers.IO) { getLocalCharactersUseCase.invoke() }.let { result ->
            when (result) {
                is Result.Failure -> {
                    mutableLocalDataState.postValue(Data(responseType = Status.GET_LOCAL_CHARACTER_ERROR, error = result.exception))
                }
                is Result.Success -> {
                    mutableLocalDataState.postValue(Data(responseType = Status.GET_LOCAL_CHARACTER_SUCCESS, data = result.data))
                }
            }
        }
    }
}