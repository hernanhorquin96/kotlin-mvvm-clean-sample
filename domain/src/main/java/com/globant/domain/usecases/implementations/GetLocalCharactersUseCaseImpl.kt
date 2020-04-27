package com.globant.domain.usecases.implementations

import com.globant.domain.entities.MarvelCharacter
import com.globant.domain.repositories.MarvelCharacterRepository
import com.globant.domain.usecases.GetLocalCharactersUseCase
import com.globant.domain.utils.Result
import org.koin.core.KoinComponent
import org.koin.core.inject

class GetLocalCharactersUseCaseImpl: KoinComponent, GetLocalCharactersUseCase {
    private val marvelCharacterRepository: MarvelCharacterRepository by inject()
    override fun invoke(): Result<List<MarvelCharacter>> = marvelCharacterRepository.getLocalCharacters()
}