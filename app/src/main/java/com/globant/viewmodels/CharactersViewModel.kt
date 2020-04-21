package com.globant.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.globant.domain.entities.MarvelCharacter
import com.globant.domain.usecases.GetCharacterUseCase
import com.globant.domain.utils.Result
import com.globant.utils.Data
import com.globant.utils.Event
import com.globant.utils.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharactersViewModel(val getCharactersUseCase: GetCharacterUseCase) : ViewModel() {

    private var mutableMainState: MutableLiveData<Event<Data<List<MarvelCharacter>>>> = MutableLiveData()
    val mainState: LiveData<Event<Data<List<MarvelCharacter>>>>
        get() {
            return mutableMainState
        }

    fun getAllCharacters() = viewModelScope.launch {
        mutableMainState.value = Event(Data(responseType = Status.LOADING))
        when (val result = withContext(Dispatchers.IO) { getCharactersUseCase.invoke() }) {
            is Result.Failure -> {
                mutableMainState.postValue(Event(Data(responseType = Status.GetCharacterError, error = result.exception)))
            }
            is Result.Success -> {
                mutableMainState.postValue(Event(Data(responseType = Status.GetCharacterSuccess, data = result.data)))
            }
        }
    }
}