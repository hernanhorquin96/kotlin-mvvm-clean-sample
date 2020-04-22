package com.globant.data.database

import com.globant.data.database.entity.MarvelCharacterRealm
import com.globant.data.mapper.CharacterMapperLocal
import com.globant.domain.entities.MarvelCharacter
import com.globant.domain.utils.Result
import io.realm.Realm

class CharacterDatabase {

    fun getCharacterById(id: Int): Result<MarvelCharacter> {
        val mapper = CharacterMapperLocal()
        Realm.getDefaultInstance().use {
            val character = it.where(MarvelCharacterRealm::class.java).equalTo(ID, id).findFirst()
            character?.let { return Result.Success(mapper.transformToCharacter(character)) }
            return Result.Failure(Exception(NOT_FOUND))
        }
    }

    fun insertOrUpdateCharacter(character: MarvelCharacter) {
        val mapperLocal = CharacterMapperLocal()
        Realm.getDefaultInstance().use {
            it.executeTransaction { realm ->
                realm.insertOrUpdate(mapperLocal.transformToRepository(character))
            }
        }
    }

    companion object {
        private const val ID = "id"
        private const val NOT_FOUND = "Character not found"
    }
}
