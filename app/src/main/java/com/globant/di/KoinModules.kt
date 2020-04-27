package com.globant.di

import com.globant.viewmodels.CharacterDetailViewModel
import com.globant.viewmodels.CharactersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel { CharactersViewModel(get(), get()) }
    viewModel { CharacterDetailViewModel(get()) }
}
