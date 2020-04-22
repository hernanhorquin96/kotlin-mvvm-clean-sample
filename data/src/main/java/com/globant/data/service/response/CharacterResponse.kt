package com.globant.data.service.response

data class CharacterResponse (
        val id: Int,
        val name: String,
        val description: String,
        val thumbnail: ThumbnailResponse
)
