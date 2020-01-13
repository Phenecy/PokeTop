package dev.phenecy.poketop.pokemon_characteristics

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TypeD(
    @SerializedName("name")
    @Expose
    private var name: String?
)