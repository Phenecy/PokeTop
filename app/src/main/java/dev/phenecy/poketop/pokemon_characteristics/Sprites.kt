package dev.phenecy.poketop.pokemon_characteristics

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Sprites(
        @SerializedName("front_default")
        @Expose var picture: String?
)