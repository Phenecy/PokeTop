package dev.phenecy.poketop.pokemon_characteristics

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Pokemon(
        @SerializedName("height")
        @Expose
        private var height: Int? = null,

        @SerializedName("id")
        @Expose
        private var id: Int? = null,

        @SerializedName("name")
        @Expose
        private var name: String? = null,

        @SerializedName("sprites")
        @Expose
        private var sprites: Sprites? = null,

        @SerializedName("stats")
        @Expose
        private var stats: List<Stat>? = null,

        @SerializedName("types")
        @Expose
        private var types: List<Type>? = null,

        @SerializedName("weight")
        @Expose
        private var weight: Int? = null
)