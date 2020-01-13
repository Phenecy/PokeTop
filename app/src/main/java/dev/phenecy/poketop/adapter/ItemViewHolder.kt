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
import org.w3c.dom.Text

public class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder, View.OnClickListener {

    var idPokemon: TextView
    var picturePokemon: ImageView
    var namePokemon: TextView

    init {
        super.itemView
        picturePokemon = itemView.findViewById(R.id.pokemon_card_image)
        namePokemon = itemView.findViewById(R.id.pokemon_card_name)
        idPokemon = itemView.findViewById(R.id.pokemon_card_id)

        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val fragmentManager: FragmentManager = (v?.context as MainActivity).supportFragmentManager
        val fragmentList: FragmentList = (fragmentManager.findFragmentByTag("FRAGMENT_LIST")) as FragmentList
        fragmentList.clickOnItem(adapterPosition)
    }

    public fun bin(item Pokemon) {
        namePokemon.text = item.name
        idPokemon.text = item.id
        val urlPicture: String? = item.getSprites().getPicture()
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