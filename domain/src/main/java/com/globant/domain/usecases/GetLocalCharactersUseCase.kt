package com.globant.domain.usecases

import com.globant.domain.entities.MarvelCharacter
import com.globant.domain.utils.Result

interface GetLocalCharactersUseCase {
    fun invoke(): Result<List<MarvelCharacter>>
}