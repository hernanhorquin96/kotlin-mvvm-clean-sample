package com.globant.myapplication

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.globant.di.viewModelsModule
import com.globant.domain.entities.MarvelCharacter
import com.globant.domain.usecases.implementations.GetCharactersUseCaseImpl
import com.globant.domain.usecases.implementations.GetLocalCharactersUseCaseImpl
import com.globant.domain.utils.Result
import com.globant.myapplication.util.testObserver
import com.globant.repositoriesModule
import com.globant.useCasesModule
import com.globant.utils.Data
import com.globant.utils.Status
import com.globant.viewmodels.CharactersViewModel
import com.google.common.truth.Truth
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.AutoCloseKoinTest
import org.mockito.MockitoAnnotations
import java.lang.Exception

class CharacterViewModelTest : AutoCloseKoinTest() {

    @ObsoleteCoroutinesApi
    private var mainThreadSurrogate = newSingleThreadContext(UI_THREAD)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private var marvelCharacterValidResult: Result.Success<List<MarvelCharacter>> = mock()
    private var marvelCharacterInvalidResult: Result.Failure = mock()
    private var characters: List<MarvelCharacter> = mock()
    private var exception: Exception = Exception(NETWORK_ERROR)
    private var getCharactersUseCase: GetCharactersUseCaseImpl = mock()
    private var getLocalCharactersUseCase: GetLocalCharactersUseCaseImpl = mock()
    private var viewModel: CharactersViewModel = CharactersViewModel(getCharactersUseCase, getLocalCharactersUseCase)

    @ExperimentalCoroutinesApi
    @ObsoleteCoroutinesApi
    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)
        startKoin {
            modules(listOf(useCasesModule, viewModelsModule, repositoriesModule))
        }
        MockitoAnnotations.initMocks(this)
    }

    @ExperimentalCoroutinesApi
    @ObsoleteCoroutinesApi
    @After
    fun after() {
        stopKoin()
        mainThreadSurrogate.close()
        Dispatchers.resetMain()
    }

    @Test
    fun onGetLocalCharactersTestSuccessful() {
        val liveDataUnderTest = viewModel.localDataState.testObserver()
        runBlocking {
            whenever(getLocalCharactersUseCase.invoke()).thenReturn(marvelCharacterValidResult)
            whenever(marvelCharacterValidResult.data).thenReturn(characters)
            viewModel.getLocalCharacters().join()
        }
        Truth.assert_()
                .that(liveDataUnderTest.observedValues)
                .isEqualTo(listOf(Data(Status.LOADING), Data(Status.GET_LOCAL_CHARACTER_SUCCESS, data = characters)))
    }

    @Test
    fun onGetRemoteCharactersTestError() {
        characters = emptyList()
        val liveDataUnderTest = viewModel.mainState.testObserver()
        runBlocking {
            whenever(getCharactersUseCase.invoke()).thenReturn(marvelCharacterInvalidResult)
            whenever(marvelCharacterInvalidResult.exception).thenReturn(exception)
            viewModel.getAllCharacters().join()
        }
        Truth.assert_()
                .that(liveDataUnderTest.observedValues)
                .isEqualTo(listOf(Data(Status.LOADING), Data(Status.GET_CHARACTER_ERROR, data = null, error = exception)))
    }

    @Test
    fun onGetRemoteCharactersSuccessful() {
        val liveDataUnderTest = viewModel.mainState.testObserver()
        runBlocking {
            whenever(getCharactersUseCase.invoke()).thenReturn(marvelCharacterValidResult)
            whenever(marvelCharacterValidResult.data).thenReturn(characters)
            viewModel.getAllCharacters().join()
        }
        Truth.assert_()
                .that(liveDataUnderTest.observedValues)
                .isEqualTo(listOf(Data(Status.LOADING), Data(Status.GET_CHARACTER_SUCCESS, data = characters)))
    }

    @Test
    fun onGetLocalCharactersTestError() {
        characters = emptyList()
        val liveDataUnderTest = viewModel.localDataState.testObserver()
        runBlocking {
            whenever(getLocalCharactersUseCase.invoke()).thenReturn(marvelCharacterInvalidResult)
            whenever(marvelCharacterInvalidResult.exception).thenReturn(exception)
            viewModel.getLocalCharacters().join()
        }
        Truth.assert_()
                .that(liveDataUnderTest.observedValues)
                .isEqualTo(listOf(Data(Status.LOADING), Data(Status.GET_LOCAL_CHARACTER_ERROR, data = null, error = exception)))
    }

    companion object {
        private const val NETWORK_ERROR = "Network Error"
        private const val UI_THREAD = "UI thread"
    }
}
