package com.globant

import com.globant.data.database.CharacterDatabase
import com.globant.data.repositories.MarvelCharacterRepositoryImpl
import com.globant.data.service.MarvelCharactersServiceImpl
import com.globant.domain.repositories.MarvelCharacterRepository
import com.globant.domain.services.MarvelCharactersService
import com.globant.domain.usecases.GetCharacterByIdUseCase
import com.globant.domain.usecases.GetCharacterUseCase
import com.globant.domain.usecases.implementations.GetCharacterByIdUseCaseImpl
import com.globant.domain.usecases.implementations.GetCharactersUseCaseImpl
import org.koin.dsl.module

val repositoriesModule = module {
    single { MarvelCharactersServiceImpl() }
    single { CharacterDatabase() }
    single<MarvelCharacterRepository> { MarvelCharacterRepositoryImpl(get(), get()) }
    single<MarvelCharactersService> { MarvelCharactersServiceImpl() }
}

val useCasesModule = module {
    single<GetCharacterByIdUseCase> { GetCharacterByIdUseCaseImpl() }
    single<GetCharacterUseCase> { GetCharactersUseCaseImpl() }
}