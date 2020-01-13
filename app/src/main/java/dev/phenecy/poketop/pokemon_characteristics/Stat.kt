package dev.phenecy.poketop.pokemon_characteristics

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Stat(
        @SerializedName("base_stat")
        @Expose
        private var baseStat: Int? = null,

        @SerializedName("stat")
        @Expose
        private var stat: StatD? = null
)