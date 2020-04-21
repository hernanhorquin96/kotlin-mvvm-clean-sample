package com.globant.domain.usecases.implementations

import com.globant.domain.services.MarvelCharactersService
import com.globant.domain.usecases.GetCharacterUseCase
import org.koin.core.KoinComponent
import org.koin.core.inject

class GetCharactersUseCaseImpl: KoinComponent, GetCharacterUseCase {
    private val marvelCharacterService: MarvelCharactersService by inject()
    override fun invoke() = marvelCharacterService.getCharacters()
}