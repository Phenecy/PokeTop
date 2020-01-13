package dev.phenecy.poketop.rest

data class Pokemon(
    val id: Int,
    val name: String,
    val baseExperience: Int,
    val height: Int,
    val isDefault: Boolean,
    val weight: Int,
    val sprites: PokemonSprites,
    val locationAreaEncounters: String,
    val abilities: List<List<Ability>>,
    val moves: List<List<Move>>,
    val stats: List<List<Stat>>,
    val types: List<List<Type>>
)

data class PokemonSprites(
    val backDefault: String?,
    val backShiny: String?,
    val frontDefault: String?,
    val frontShiny: String?,
    val backFemale: String?,
    val backShinyFemale: String?,
    val frontFemale: String?,
    val frontShinyFemale: String?

)

data class Ability(
    val id: Int,
    val name: String,
    val description: String
)

data class Move(
    val id: Int,
    val name: String,
    val description: String
)

data class Stat(
    val id: Int,
    val name: String,
    val baseStat: Int,
    val effort: Int
)

data class Type(
    val id: Int,
    val name: String,
    val image: String
)