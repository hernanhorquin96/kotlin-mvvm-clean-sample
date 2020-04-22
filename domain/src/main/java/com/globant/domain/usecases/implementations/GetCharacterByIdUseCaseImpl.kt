package com.globant.domain.usecases.implementations

import com.globant.domain.repositories.MarvelCharacterRepository
import com.globant.domain.usecases.GetCharacterByIdUseCase
import org.koin.core.KoinComponent
import org.koin.core.inject

class GetCharacterByIdUseCaseImpl: KoinComponent, GetCharacterByIdUseCase {
    private val marvelCharacterRepository: MarvelCharacterRepository by inject()
    override fun invoke(id: Int, getFromRemote: Boolean) = marvelCharacterRepository.getCharacterById(id, getFromRemote)
}
