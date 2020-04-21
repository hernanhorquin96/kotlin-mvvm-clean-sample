package com.globant.domain.usecases

import com.globant.domain.entities.MarvelCharacter
import com.globant.domain.utils.Result

interface GetCharacterByIdUseCase {
    fun invoke(id: Int, getFromRemote: Boolean) : Result<MarvelCharacter>
}