package com.globant.myapplication

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.globant.di.viewModelsModule
import com.globant.domain.entities.MarvelCharacter
import com.globant.domain.usecases.implementations.GetCharacterByIdUseCaseImpl
import com.globant.domain.utils.Result
import com.globant.myapplication.util.testObserver
import com.globant.repositoriesModule
import com.globant.useCasesModule
import com.globant.utils.Data
import com.globant.utils.Status
import com.globant.viewmodels.CharacterDetailViewModel
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

class CharacterDetailViewModelTest : AutoCloseKoinTest() {

    @ObsoleteCoroutinesApi
    private var mainThreadSurrogate = newSingleThreadContext(UI_THREAD)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var viewModel: CharacterDetailViewModel
    private lateinit var marvelCharacterValidResult: Result.Success<MarvelCharacter>
    private lateinit var marvelCharacterInvalidResult: Result.Failure
    private lateinit var characters: MarvelCharacter
    private lateinit var exception: Exception
    private lateinit var getCharacterByIdUseCase: GetCharacterByIdUseCaseImpl

    @ExperimentalCoroutinesApi
    @ObsoleteCoroutinesApi
    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)
        startKoin {
            modules(listOf(useCasesModule, viewModelsModule, repositoriesModule))
        }
        marvelCharacterInvalidResult = mock()
        marvelCharacterValidResult = mock()
        getCharacterByIdUseCase = mock()
        exception = Exception(NETWORK_ERROR)
        characters = mock()
        viewModel = CharacterDetailViewModel(getCharacterByIdUseCase)
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
    fun onGetRemoteCharactersTestError() {
        val liveDataUnderTest = viewModel.mainState.testObserver()
        runBlocking {
            whenever(getCharacterByIdUseCase.invoke(INVALID_ID, true)).thenReturn(marvelCharacterInvalidResult)
            whenever(marvelCharacterInvalidResult.exception).thenReturn(exception)
            viewModel.getCharacterById(INVALID_ID).join()
        }
        Truth.assert_()
                .that(liveDataUnderTest.observedValues)
                .isEqualTo(listOf(Data(Status.LOADING), Data(Status.GET_CHARACTER_BY_ID_ERROR, data = null, error = exception)))
    }

    @Test
    fun onGetRemoteCharactersSuccessful() {
        val liveDataUnderTest = viewModel.mainState.testObserver()
        runBlocking {
            whenever(getCharacterByIdUseCase.invoke(VALID_ID, true)).thenReturn(marvelCharacterValidResult)
            whenever(marvelCharacterValidResult.data).thenReturn(characters)
            viewModel.getCharacterById(VALID_ID).join()
        }
        Truth.assert_()
                .that(liveDataUnderTest.observedValues)
                .isEqualTo(listOf(Data(Status.LOADING), Data(Status.GET_CHARACTER_BY_ID_SUCCESS, data = characters)))
    }

    class TestObserver<T> : Observer<T> {
        val observedValues = mutableListOf<T?>()
        override fun onChanged(value: T?) {
            observedValues.add(value)
        }
    }

    companion object {
        private const val NETWORK_ERROR = "Network Error"
        private const val UI_THREAD = "UI thread"
        private const val VALID_ID = 1017100
        private const val INVALID_ID = -1
    }
}
