package com.globant.data.repositories

import com.globant.data.database.CharacterDatabase
import com.globant.domain.entities.MarvelCharacter
import com.globant.domain.repositories.MarvelCharacterRepository
import com.globant.domain.utils.Result

class MarvelCharacterRepositoryImpl(private val characterDatabase: CharacterDatabase) : MarvelCharacterRepository {

    override fun getCharacterById(id: Int, getFromRemote: Boolean): Result<MarvelCharacter> =
            characterDatabase.getCharacterById(id)

    private fun insertOrUpdateCharacter(character: MarvelCharacter) {
        characterDatabase.insertOrUpdateCharacter(character)
    }

    override fun getLocalCharacters(): Result<List<MarvelCharacter>> =
            characterDatabase.getCharacters()

    override fun insertCharacters(characters: List<MarvelCharacter>) {
        characterDatabase.insertCharacters(characters)
    }
}
