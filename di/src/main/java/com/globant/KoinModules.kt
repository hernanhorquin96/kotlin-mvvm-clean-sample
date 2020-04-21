package com.globant

import com.globant.data.database.CharacterDatabase
import com.globant.data.repositories.MarvelCharacterRepositoryImpl
import com.globant.data.service.MarvelCharactersServiceImpl
import com.globant.domain.repositories.MarvelCharacterRepository
import com.globant.domain.usecases.GetCharacterByIdUseCase
import com.globant.domain.usecases.GetCharactersUseCase
import org.koin.dsl.module

val repositoriesModule = module {
    single { MarvelCharactersServiceImpl() }
    single { CharacterDatabase() }
    single<MarvelCharacterRepository> { MarvelCharacterRepositoryImpl(get(), get()) }
}


val useCasesModule = module {
    single { GetCharacterByIdUseCase() }
    single { GetCharactersUseCase() }
}