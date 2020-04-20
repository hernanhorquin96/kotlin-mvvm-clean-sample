package com.globant.data.service

import com.globant.data.MarvelRequestGenerator
import com.globant.data.ZERO
import com.globant.data.mapper.CharacterMapperService
import com.globant.data.service.api.MarvelApi
import com.globant.domain.entities.MarvelCharacter
import com.globant.domain.services.MarvelCharactersService
import com.globant.domain.utils.Result

class CharacterService : MarvelCharactersService {

    private val api: MarvelRequestGenerator = MarvelRequestGenerator()
    private val mapper: CharacterMapperService = CharacterMapperService()

    override fun getCharacterById(id: Int): Result<MarvelCharacter> {
        val callResponse = api.createService(MarvelApi::class.java).getCharacterById(id)
        val response = callResponse.execute()
        if (response.isSuccessful)
            response.body()?.data?.characters?.get(ZERO)?.let { mapper.transformToCharacter(it) }?.let { return Result.Success(it) }

        return Result.Failure(Exception(response.message()))
    }

    override fun getCharacters(): Result<List<MarvelCharacter>> {
        val callResponse = api.createService(MarvelApi::class.java).getCharacters()
        val response = callResponse.execute()
        if (response.isSuccessful)
            response.body()?.data?.characters?.let { mapper.transform(it) }?.let { return Result.Success(it) }

        return Result.Failure(Exception(response.message()))
    }

}
