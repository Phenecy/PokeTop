package dev.phenecy.poketop.pokemon_characteristics

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Pokemon(
        @SerializedName("height")
        @Expose
        public var height: Int? = null,

        @SerializedName("id")
        @Expose var id: Int? = null,

        @SerializedName("name")
        @Expose var name: String? = null,

        @SerializedName("sprites")
        @Expose
        public var sprites: Sprites? = null,

        @SerializedName("stats")
        @Expose
        public var stats: List<Stat>? = null,

        @SerializedName("types")
        @Expose
        public var types: List<Type>? = null,

        @SerializedName("weight")
        @Expose
        public var weight: Int? = null
)