package dev.phenecy.poketop.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import dev.phenecy.poketop.R
import dev.phenecy.poketop.rest.Pokemon
import kotlinx.android.synthetic.main.pokemon_list_item.view.*

class PokemonListRowAdapter : BaseAdapter<PokemonListRowAdapter.PokemonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.pokemon_list_item_row, parent, false)
        return PokemonViewHolder(view)
    }

    class PokemonViewHolder(view: View) : BaseAdapter.BaseViewHolder(view) {

//        val id: Int = 0
//        val num: String = ""
//        val name: String = ""
//        val img: String = ""
//        val type: List<String> = listOf()
//        val height: String = ""
//        val weight: String = ""
//        val candy: String = ""
//        val candy_count: Int = 0
//        val egg: String = ""
//        val spawn_chance: Double = 0.0
//        val avg_spawns: Int = 0
//        val spawn_time: String = ""
//        val multipliers: List<Double> = listOf()
//        val weaknesses: List<String> = listOf()
//        val next_evolution: List<Evolution> = listOf()

        init {
            itemView.setOnClickListener {
            }
        }
        override fun bind(item: Any) {
            let {
                item as Pokemon
                Glide.with(view.context).load(item.image).into(view.pokemon_card_image)
                view.pokemon_card_id.text = item.id.toString()
                view.pokemon_card_name.text = item.name
            }
        }
    }
}