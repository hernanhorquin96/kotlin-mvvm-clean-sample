package com.globant

import com.globant.data.database.CharacterDatabase
import com.globant.data.repositories.MarvelCharacterRepositoryImpl
import com.globant.data.service.MarvelCharactersServiceImpl
import com.globant.domain.repositories.MarvelCharacterRepository
import com.globant.domain.services.MarvelCharactersService
import com.globant.domain.usecases.GetCharacterByIdUseCase
import com.globant.domain.usecases.GetCharacterUseCase
import com.globant.domain.usecases.GetLocalCharactersUseCase
import com.globant.domain.usecases.implementations.GetCharacterByIdUseCaseImpl
import com.globant.domain.usecases.implementations.GetCharactersUseCaseImpl
import com.globant.domain.usecases.implementations.GetLocalCharactersUseCaseImpl
import org.koin.dsl.module

val repositoriesModule = module {
    single { CharacterDatabase() }
    single<MarvelCharacterRepository>{ MarvelCharacterRepositoryImpl(get(),get()) }
    single<MarvelCharactersService> { MarvelCharactersServiceImpl() }
}

val useCasesModule = module {
    factory<GetCharacterByIdUseCase> { GetCharacterByIdUseCaseImpl() }
    factory<GetCharacterUseCase> { GetCharactersUseCaseImpl() }
    factory<GetLocalCharactersUseCase> { GetLocalCharactersUseCaseImpl() }
}