package com.globant.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.globant.adapter.CharacterAdapter
import com.globant.domain.entities.MarvelCharacter
import com.globant.myapplication.R
import com.globant.utils.Data
import com.globant.utils.Event
import com.globant.utils.Status
import com.globant.viewmodels.CharactersViewModel
import kotlinx.android.synthetic.main.activity_characters.button_clear
import kotlinx.android.synthetic.main.activity_characters.button_local_data
import kotlinx.android.synthetic.main.activity_characters.button_refresh
import kotlinx.android.synthetic.main.activity_characters.progress_bar
import kotlinx.android.synthetic.main.activity_characters.recycler_view_characters
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharactersActivity : AppCompatActivity() {

    private val viewModel by viewModel<CharactersViewModel>()
    private val adapter = CharacterAdapter { character ->
        val characterFragment = CharacterDetailFragment.newInstance(character.id)
        characterFragment.show(supportFragmentManager, getString(R.string.tag))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_characters)
        button_refresh.setOnClickListener {
            viewModel.getAllCharacters()
        }
        button_local_data.setOnClickListener {
            viewModel.getLocalCharacters()
        }
        button_clear.setOnClickListener {
            adapter.update(emptyList())
        }
        viewModel.localDataState.observe(::getLifecycle, ::showLocalData)
        viewModel.mainState.observe(::getLifecycle, ::updateUI)
    }

    private fun showLocalData(charactersData: Event<Data<List<MarvelCharacter>>>) {
        when (charactersData.peekContent().responseType) {
            Status.GET_LOCAL_CHARACTER_ERROR -> {
                showErrorMsg(charactersData.peekContent().error?.message)
            }
            Status.LOADING -> {
                progress_bar.visibility = View.VISIBLE
            }
            Status.GET_LOCAL_CHARACTER_SUCCESS -> {
                showCharacters(charactersData.peekContent().data)
            }
        }
    }

    private fun updateUI(charactersData: Event<Data<List<MarvelCharacter>>>) {
        when (charactersData.peekContent().responseType) {
            Status.GET_CHARACTER_ERROR -> {
                showErrorMsg(charactersData.peekContent().error?.message)
            }
            Status.LOADING -> {
                progress_bar.visibility = View.VISIBLE
            }
            Status.GET_CHARACTER_SUCCESS -> {
                showCharacters(charactersData.peekContent().data)
            }
        }
    }

    private fun showErrorMsg(errorMessage: String?) {
        progress_bar.visibility = View.GONE
        errorMessage?.let {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }
    }

    private fun showCharacters(characters: List<MarvelCharacter>?) {
        progress_bar.visibility = View.GONE
        button_clear.visibility = View.VISIBLE
        characters?.let {
            adapter.update(characters)
            recycler_view_characters.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
            recycler_view_characters.adapter = adapter
        }
    }
}
