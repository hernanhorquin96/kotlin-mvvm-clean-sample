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
import kotlinx.android.synthetic.main.activity_characters.progress_bar
import kotlinx.android.synthetic.main.activity_characters.recycler_view_characters
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharactersActivity : AppCompatActivity() {

    private val viewModel by viewModel<CharactersViewModel>()
    private lateinit var adapter: CharacterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_characters)
        viewModel.getAllCharacters()
        viewModel.mainState.observe(::getLifecycle, ::updateUI)
    }

    private fun updateUI(charactersData: Event<Data<List<MarvelCharacter>>>) {
        when (charactersData.peekContent().responseType) {
            Status.GetCharacterError -> {
                progress_bar.visibility = View.GONE
                charactersData.peekContent().error?.message?.let {
                    Toast.makeText(this, it, Toast.LENGTH_LONG).show()
                }
            }
            Status.LOADING -> {
                progress_bar.visibility = View.VISIBLE
            }
            Status.GetCharacterSuccess -> {
                progress_bar.visibility = View.GONE
                charactersData.peekContent().data?.let {
                    adapter = CharacterAdapter(it) {
                        //TODO: in next PR i will open the fragment dialog from here
                    }
                    recycler_view_characters.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
                    recycler_view_characters.adapter = adapter
                }
            }
        }
    }
}
