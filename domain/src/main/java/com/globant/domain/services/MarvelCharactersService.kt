package com.globant.domain.services

import com.globant.domain.entities.MarvelCharacter
import com.globant.domain.utils.Result

interface MarvelCharactersService {
    fun getCharacters(): Result<List<MarvelCharacter>>
    fun getCharacterById(id: Int): Result<MarvelCharacter>
}