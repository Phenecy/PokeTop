package dev.phenecy.poketop.rest

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonApi {

    @GET("pokemon/")
    fun getPokemonList(): Observable<List<Pokemon>>

    @GET("pokemon/{id}")
    fun getPokemonData(
        @Path("id") id: String
    ): Observable<Pokemon>
}