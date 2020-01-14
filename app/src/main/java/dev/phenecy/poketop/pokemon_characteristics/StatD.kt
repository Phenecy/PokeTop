package dev.phenecy.poketop.pokemon_characteristics

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class StatD(
        @SerializedName("name")
        @Expose
        var name: String? = null
)