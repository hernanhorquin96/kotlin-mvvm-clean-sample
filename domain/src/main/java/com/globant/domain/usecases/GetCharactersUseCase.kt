package com.globant.domain.usecases

import com.globant.domain.services.MarvelCharactersService
import org.koin.core.KoinComponent
import org.koin.core.inject

class GetCharactersUseCase: KoinComponent {
    private val marvelCharacterService: MarvelCharactersService by inject()
    operator fun invoke() = marvelCharacterService.getCharacters()
}