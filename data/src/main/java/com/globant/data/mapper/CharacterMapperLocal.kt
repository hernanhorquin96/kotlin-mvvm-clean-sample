package com.globant.data.mapper

import com.globant.data.database.entity.MarvelCharacterRealm
import com.globant.data.database.entity.ThumbnailRealm
import com.globant.domain.entities.MarvelCharacter
import com.globant.domain.entities.Thumbnail

class CharacterMapperLocal : BaseMapperRepository<MarvelCharacterRealm, MarvelCharacter> {

    override fun transformToCharacter(type: MarvelCharacterRealm): MarvelCharacter = MarvelCharacter(
            type.id,
            type.name,
            type.description,
            transformToThumbnail(type.thumbnail)
    )

    override fun transformToRepository(type: MarvelCharacter): MarvelCharacterRealm = MarvelCharacterRealm(
            type.id,
            type.name,
            type.description,
            transformToThumbnailRealm(type.thumbnail)
    )

    fun transformToThumbnailRealm(thumbnailResponse: Thumbnail): ThumbnailRealm = ThumbnailRealm(
            thumbnailResponse.path,
            thumbnailResponse.extension
    )

    fun transformToThumbnail(thumbnail: ThumbnailRealm?) = Thumbnail(
            thumbnail?.path.toString(),
            thumbnail?.extension.toString()
    )

    fun transformToListOfCharacters(charactersRealm: List<MarvelCharacterRealm>) = charactersRealm.map { transformToCharacter(it) }
}
