package dev.phenecy.poketop.api

import dev.phenecy.poketop.pokemon_characteristics.Pokemon
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

public interface RetrofitService {

    @GET("pokemon/{id}")
    fun getPokemon(@Path("id") id: Int): Call<Pokemon>

    @GET("pokemon")
    fun getPokemonCount(): Call<ResponseBody>
}