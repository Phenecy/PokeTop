package dev.phenecy.poketop.pokemon_characteristics

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Pokemon(
        @SerializedName("height")
        @Expose var height: Int? = null,

        @SerializedName("id")
        @Expose var id: Int? = null,

        @SerializedName("name")
        @Expose var name: String? = null,

        @SerializedName("sprites")
        @Expose var sprites: Sprites? = null,

        @SerializedName("stats")
        @Expose var stats: List<Stat>? = null,

        @SerializedName("types")
        @Expose var types: List<Type>? = null,

        @SerializedName("weight")
        @Expose var weight: Int? = null
)