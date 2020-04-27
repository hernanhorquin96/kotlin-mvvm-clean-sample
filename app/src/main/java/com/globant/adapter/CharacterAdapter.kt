package com.globant.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.globant.domain.entities.MarvelCharacter
import com.globant.extensions.getImageByUrl
import com.globant.myapplication.R
import kotlinx.android.synthetic.main.item_character.view.image_thumbnail
import kotlinx.android.synthetic.main.item_character.view.tv_item

class CharacterAdapter(
        private val onCharacterClicked: (MarvelCharacter) -> Unit
) : RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {

    private val characters = mutableListOf<MarvelCharacter>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                            R.layout.item_character,
                            parent,
                            false
                    ), onCharacterClicked
            )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(characters[position])
    }

    override fun getItemCount(): Int = characters.size

    fun update(items: List<MarvelCharacter>) {
        characters.clear()
        characters.addAll(items)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View, private val onCharactrClicked: (MarvelCharacter) -> Unit) :
            RecyclerView.ViewHolder(itemView) {

        fun bind(item: MarvelCharacter) = with(itemView) {
            tv_item.text = item.name
            image_thumbnail.getImageByUrl("${item.thumbnail.path}$DOT${item.thumbnail.extension}")
            setOnClickListener { onCharactrClicked(item) }
        }

        companion object {
            private const val DOT = "."
        }
    }
}