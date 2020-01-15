package dev.phenecy.poketop.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.phenecy.poketop.MainActivity
import dev.phenecy.poketop.R
import dev.phenecy.poketop.fragments.FragmentList
import dev.phenecy.poketop.pokemon_characteristics.Pokemon
import org.w3c.dom.Text

class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    var idPokemon: TextView = itemView.findViewById(R.id.pokemon_card_id)
    var picturePokemon: ImageView = itemView.findViewById(R.id.pokemon_card_image)
    var namePokemon: TextView = itemView.findViewById(R.id.pokemon_card_name)

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val fragmentManager: FragmentManager = (v?.context as MainActivity).supportFragmentManager
        val fragmentList: FragmentList = fragmentManager.findFragmentByTag("FRAGMENT_LIST") as FragmentList
        fragmentList.clickOnItem(adapterPosition)
    }

    fun bind(item: Pokemon) {
        namePokemon.text = item.name
        idPokemon.text = item.id.toString()
        val urlPicture: String? = item.sprites?.picture
        if (urlPicture != null) {
            Glide
                    .with(itemView.context)
                    .load(urlPicture)
                    .into(picturePokemon)
        } else {
            picturePokemon.setImageResource(R.drawable.image_not_found)
        }
    }
}