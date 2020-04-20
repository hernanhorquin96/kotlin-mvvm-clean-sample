package com.globant.data.repositories

import com.globant.data.database.CharacterDatabase
import com.globant.data.service.MarvelCharactersServiceImpl
import com.globant.domain.entities.MarvelCharacter
import com.globant.domain.repositories.MarvelCharacterRepository
import com.globant.domain.utils.Result

class MarvelCharacterRepositoryImpl(
        private val marvelCharactersServiceImpl: MarvelCharactersServiceImpl,
        private val characterDatabase: CharacterDatabase
) : MarvelCharacterRepository {

    override fun getCharacterById(id: Int, getFromRemote: Boolean): Result<MarvelCharacter> =
        if (getFromRemote) {
            val marvelCharacterResult = marvelCharactersServiceImpl.getCharacterById(id)
            if (marvelCharacterResult is Result.Success) {
                insertOrUpdateCharacter(marvelCharacterResult.data)
            }
            marvelCharacterResult
        } else {
            characterDatabase.getCharacterById(id)
        }

    private fun insertOrUpdateCharacter(character: MarvelCharacter) {
        characterDatabase.insertOrUpdateCharacter(character)
    }
}
