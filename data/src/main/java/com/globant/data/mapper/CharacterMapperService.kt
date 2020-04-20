package com.globant.data.mapper

import com.globant.data.service.response.CharacterResponse
import com.globant.domain.entities.MarvelCharacter

open class CharacterMapperService : BaseMapperRepository<CharacterResponse, MarvelCharacter> {

    override fun transformToCharacter(type: CharacterResponse): MarvelCharacter =
            MarvelCharacter(
            type.id,
            type.name,
            type.description
        )

    override fun transformToRepository(type: MarvelCharacter): CharacterResponse =
        CharacterResponse(
            type.id,
            type.name,
            type.description
        )

    fun transform(charactersResponse: List<CharacterResponse>): List<MarvelCharacter>
            = charactersResponse.map { transformToCharacter(it) }
}
