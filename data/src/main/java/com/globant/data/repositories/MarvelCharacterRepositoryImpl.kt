package com.globant.data.repositories

import com.globant.data.NOT_FOUND
import com.globant.data.database.CharacterDatabase
import com.globant.domain.entities.MarvelCharacter
import com.globant.domain.repositories.MarvelCharacterRepository
import com.globant.domain.services.MarvelCharactersService
import com.globant.domain.utils.Result

class MarvelCharacterRepositoryImpl(
        private val marvelCharacterServiceImpl: MarvelCharactersService,
        private val characterDatabase: CharacterDatabase
) : MarvelCharacterRepository {

    override fun getCharacterById(id: Int, getFromRemote: Boolean): Result<MarvelCharacter> =
            characterDatabase.getCharacterById(id)

    private fun insertOrUpdateCharacter(character: MarvelCharacter) {
        characterDatabase.insertOrUpdateCharacter(character)
    }

    override fun getLocalCharacters(): Result<List<MarvelCharacter>> =
            characterDatabase.getCharacters()

    override fun getCharacters(): Result<List<MarvelCharacter>> {
        marvelCharacterServiceImpl.getCharacters().let {
            return when (it) {
                is Result.Success -> {
                    insertCharacters(it.data)
                    it
                }
                is Result.Failure -> {
                    characterDatabase.getCharacters()
                }
                else -> {
                    Result.Failure(Exception(NOT_FOUND))
                }
            }
        }
    }

    override fun insertCharacters(characters: List<MarvelCharacter>) {
        characterDatabase.insertCharacters(characters)
    }
}
