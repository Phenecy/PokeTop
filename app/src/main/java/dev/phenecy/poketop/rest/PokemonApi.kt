package dev.phenecy.poketop.rest

import io.reactivex.Observable
import retrofit2.http.GET

interface PokemonApi {

    @GET("pokedex.json")
    fun getPokemonList(): Observable<PokemonBase>
}