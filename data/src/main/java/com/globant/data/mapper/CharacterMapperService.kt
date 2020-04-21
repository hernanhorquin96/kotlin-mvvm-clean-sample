package com.globant.data.mapper

import com.globant.data.database.ThumbnailResponse
import com.globant.data.service.response.CharacterResponse
import com.globant.domain.entities.MarvelCharacter
import com.globant.domain.entities.Thumbnail

open class CharacterMapperService : BaseMapperRepository<CharacterResponse, MarvelCharacter> {

    override fun transformToCharacter(type: CharacterResponse): MarvelCharacter =
            MarvelCharacter(
                    type.id,
                    type.name,
                    type.description,
                    transformToThumbnail(type.thumbnail)
            )

    override fun transformToRepository(type: MarvelCharacter): CharacterResponse =
            CharacterResponse(
                    type.id,
                    type.name,
                    type.description,
                    transformToThumbnailResponse(type.thumbnail)
            )

    fun transformToThumbnail(thumbnailResponse: ThumbnailResponse): Thumbnail = Thumbnail(
            thumbnailResponse.path,
            thumbnailResponse.extension
    )

    fun transformToThumbnailResponse(thumbnail: Thumbnail): ThumbnailResponse
            = ThumbnailResponse(
            thumbnail.path,
            thumbnail.extension
    )

    fun transform(charactersResponse: List<CharacterResponse>): List<MarvelCharacter> = charactersResponse.map { transformToCharacter(it) }
}
