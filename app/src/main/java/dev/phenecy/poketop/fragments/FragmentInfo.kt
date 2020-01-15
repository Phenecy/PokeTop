package dev.phenecy.poketop.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import dev.phenecy.poketop.R


class FragmentInfo : Fragment() {

    private var textName: TextView? = null
    private var textType: TextView? = null
    private var textHeight: TextView? = null
    private var textWeight: TextView? = null
    private var textStats: TextView? = null
    private var imagePokemon: ImageView? = null
    private var buttonBack: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_info, container, false)
        textName = view.findViewById<View>(R.id.text_info_name) as TextView
        textType = view.findViewById<View>(R.id.text_type) as TextView
        textHeight = view.findViewById<View>(R.id.text_height) as TextView
        textWeight = view.findViewById<View>(R.id.text_weight) as TextView
        textStats = view.findViewById<View>(R.id.text_values_stats) as TextView
        imagePokemon =
            view.findViewById<View>(R.id.image_info_pokemon) as ImageView
        buttonBack =
            view.findViewById<View>(R.id.button_back) as Button
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        buttonBack!!.setOnClickListener { activity?.supportFragmentManager?.popBackStack() }
        setInfo()
    }

    private fun setInfo() {
        val bundle: Bundle? = arguments
        textName!!.text = bundle?.getString("name")
        textType!!.text = bundle?.getString("types")
        textHeight!!.text = bundle?.getString("height")
        textWeight!!.text = bundle?.getString("weight")
        textStats!!.text = bundle?.getString("stats")
        val urlPicture = bundle?.getString("urlPicture")
        if (urlPicture != null) {
            Glide.with(imagePokemon!!.context)
                .load(urlPicture)
                .error(R.drawable.image_not_found)
                .into(imagePokemon!!)
        } else {
            imagePokemon!!.setImageResource(R.drawable.image_not_found)
        }
    }

}