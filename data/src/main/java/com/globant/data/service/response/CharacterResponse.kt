package com.globant.data.service.response

import com.globant.data.database.ThumbnailResponse

class CharacterResponse (
        val id: Int,
        val name: String,
        val description: String,
        val thumbnail: ThumbnailResponse
)
