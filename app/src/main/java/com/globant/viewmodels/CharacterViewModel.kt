package com.globant.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.globant.domain.entities.MarvelCharacter
import com.globant.domain.usecases.GetCharacterByIdUseCase
import com.globant.domain.usecases.implementations.GetCharacterByIdUseCaseImpl
import com.globant.domain.utils.Result
import com.globant.utils.Data
import com.globant.utils.Event
import com.globant.utils.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharacterViewModel(val getCharacterById: GetCharacterByIdUseCase) : ViewModel() {

    private var mutableMainState: MutableLiveData<Event<Data<MarvelCharacter>>> = MutableLiveData()
    val mainState: LiveData<Event<Data<MarvelCharacter>>>
        get() {
            return mutableMainState
        }

    fun onSearchRemoteClicked(id: Int) = viewModelScope.launch {
        mutableMainState.value = Event(Data(responseType = Status.LOADING))
        when (val result = withContext(Dispatchers.IO) { getCharacterById.invoke(id, true) }) {
            is Result.Failure -> {
                mutableMainState.postValue(Event(Data(responseType = Status.ERROR, error = result.exception)))
            }
            is Result.Success -> {
                mutableMainState.postValue(Event(Data(responseType = Status.SUCCESSFUL, data = result.data)))
            }
        }
    }

    fun onSearchLocalClicked(id: Int) = viewModelScope.launch {
        mutableMainState.value = Event(Data(responseType = Status.LOADING))
        when (val result = withContext(Dispatchers.IO) { getCharacterById.invoke(id, false) }) {
            is Result.Failure -> {
                mutableMainState.postValue(Event(Data(responseType = Status.ERROR, error = result.exception)))
            }
            is Result.Success -> {
                mutableMainState.postValue(Event(Data(responseType = Status.SUCCESSFUL, data = result.data)))
            }
        }
    }
}


