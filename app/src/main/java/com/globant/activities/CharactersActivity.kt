package com.globant.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.globant.domain.entities.MarvelCharacter
import com.globant.myapplication.R
import com.globant.utils.Data
import com.globant.utils.Event
import com.globant.utils.Status
import com.globant.viewmodels.CharactersViewModel
import kotlinx.android.synthetic.main.activity_main.progress
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharactersActivity : AppCompatActivity() {

    private val viewModel by viewModel<CharactersViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_characters)
        viewModel.getAllCharacters()
        viewModel.mainState.observe(::getLifecycle, ::updateUI)
    }

    private fun updateUI(charactersData: Event<Data<List<MarvelCharacter>>>) {
        when (charactersData.peekContent().responseType) {
            Status.GetCharacterError -> {
                progress.visibility = View.GONE
                charactersData.peekContent().error?.message?.let {
                    Toast.makeText(this, it, Toast.LENGTH_LONG).show()
                }
            }
            Status.LOADING -> {
                progress.visibility = View.VISIBLE
            }
            Status.GetCharacterSuccess -> {
                progress.visibility = View.GONE
            }
        }
    }
}
