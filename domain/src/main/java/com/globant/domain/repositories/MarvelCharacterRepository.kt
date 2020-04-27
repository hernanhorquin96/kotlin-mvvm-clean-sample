package com.globant.domain.repositories

import com.globant.domain.entities.MarvelCharacter
import com.globant.domain.utils.Result

interface MarvelCharacterRepository {
    fun getCharacterById(id: Int, getFromRemote: Boolean): Result<MarvelCharacter>
    fun insertCharacters(characters: List<MarvelCharacter>)
    fun getLocalCharacters(): Result<List<MarvelCharacter>>
    fun getCharacters(): Result<List<MarvelCharacter>>
}
