package com.globant.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.globant.domain.entities.MarvelCharacter
import com.globant.extensions.getImageByUrl
import com.globant.myapplication.R
import com.globant.utils.Data
import com.globant.utils.Event
import com.globant.utils.Status
import com.globant.viewmodels.CharacterDetailViewModel
import kotlinx.android.synthetic.main.activity_main.progress
import kotlinx.android.synthetic.main.fragment_character_detail.img_character
import kotlinx.android.synthetic.main.fragment_character_detail.progress_bar
import kotlinx.android.synthetic.main.fragment_character_detail.txt_character_description
import kotlinx.android.synthetic.main.fragment_character_detail.txt_character_name
import kotlinx.android.synthetic.main.fragment_character_detail.view.btn_close
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharacterDetailFragment : DialogFragment() {

    private val viewModel by viewModel<CharacterDetailViewModel>()

    private fun updateUI(character: Event<Data<MarvelCharacter>>) {
        when (character.peekContent().responseType) {
            Status.GetCharacterByIdError -> {
                progress_bar.visibility = View.GONE
                character.peekContent().error?.message?.let {
                    Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                }
            }
            Status.LOADING -> {
                progress_bar.visibility = View.VISIBLE
            }
            Status.GetCharacterByIdSuccess -> {
                progress_bar.visibility = View.GONE
                character.peekContent().data?.let {
                    img_character?.getImageByUrl("${it.thumbnail.path}$DOT${it.thumbnail.extension}")
                    txt_character_name?.text = it.name
                    txt_character_description?.text = it.description
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_character_detail, container, false)
        arguments?.getInt(CHARACTER_ID)?.let {
            viewModel.getCharacterById(it)
        }
        viewModel.mainState.observe(::getLifecycle, ::updateUI)
        rootView.btn_close.setOnClickListener { this.dismiss() }
        return rootView
    }

    companion object {
        private const val CHARACTER_ID = "character_id"
        private const val DOT = "."
        fun newInstance(character_id: Int): CharacterDetailFragment {
            val args = Bundle()
            args.putInt(CHARACTER_ID, character_id)
            val fragment = CharacterDetailFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
