package dev.phenecy.poketop.mvp

import android.os.Bundle
import android.util.Log
import dev.phenecy.poketop.pokemon_characteristics.Pokemon
import dev.phenecy.poketop.pokemon_characteristics.Stat
import dev.phenecy.poketop.pokemon_characteristics.Type
import java.util.*

public class Presenter : MainContract.PresenterLayer {

    private var mView: MainContract.ViewLayer? = null
    private var mRepository: MainContract.ModelLayer? = Repository(this)
    private var sortеdArray: List<Pokemon>? = null
    private var isGetMore = false
    private var isLoadOther = false
    private var isSortByAttack = false
    private var isSortByDefense = false
    private var isSortByHp = false

    override fun getItems() {
        if (isSortRequired()) mView?.createAdapter(sortеdArray) else mRepository?.getData()
    }

    override fun getMoreItems() {
        isGetMore = true
        mRepository?.loadMoreData()
    }

    override fun loadOtherItems() {
        isLoadOther = true
        mRepository?.loadOtherData()
    }

    override fun dataIsPrepared() {
        mView?.createAdapter(mRepository?.getArray())
    }

    override fun dataIsLoaded(data: List<*>?) {
        Log.v("RetrofitCheck", "dataIsLoaded")
        saveData(data)
        if (isGetMore) {
            isGetMore = false
            if (isSortRequired()) {
                formingSortedArray()
                mView?.setNewSortedList(sortеdArray)
            } else {
                mView?.insertItems()
            }
        } else {
            if (isLoadOther) {
                Log.v("RetrofitCheck", "isLoadOther$isLoadOther")
                isLoadOther = false
                mView?.setOtherItems()
            } else {
                mView?.createAdapter(mRepository?.getArray())
            }
        }
    }

    override fun getInfo(index: Int): Bundle? {
        val information = Bundle()
        val pokemon: Pokemon
        pokemon = if (isSortRequired()) sortеdArray!![index] else mRepository?.getPokemonInfo(index)!!
        val listTypes: List<Type>? = pokemon.types
        val listStats: List<Stat>? = pokemon.stats
        information.putString("name", pokemon.name)
        information.putString("types", getStringTypes(listTypes!!))
        information.putString("height", "Height: " + pokemon.height)
        information.putString("weight", "Weight: " + pokemon.weight)
        information.putString("stats", getStringStats(listStats!!))
        information.putString("urlPicture", pokemon.sprites?.picture)
        return information
    }

    override fun sortData(checkBoxId: Int, isChecked: Boolean) {
        changeSortMode(checkBoxId, isChecked)
        if (mRepository?.getSizeArray() !== 0) {
            if (isSortRequired()) {
                formingSortedArray()
                mView?.setSortedList(sortеdArray)
            } else {
                mView?.setUnsortedList(mRepository?.getArray())
            }
        }
    }

    override fun isEmpty() {
        isGetMore = false
        isLoadOther = false
        if (isLoadOther || mRepository?.getSizeArray()!! < mRepository?.getCountPokemons()!!)
            mView?.showError("Failed to get data from server.") else mView?.allItemsWasLoaded()
    }

    override fun setLinkOnView(view: MainContract.ViewLayer?) {
        mView = view
    }

    private fun saveData(data: List<*>?) {
        Log.v("RetrofitCheck", "Save")
        if (isLoadOther) mRepository?.clearArray()
        mRepository?.saveData(data)
    }

//TODO: ПЕРЕДЕЛАТЬ

    private fun changeSortMode(checkBoxId: Int, isChecked: Boolean) {
        when (checkBoxId) {
//            R.id.check_attack -> isSortByAttack = isChecked
//            R.id.check_defense -> isSortByDefense = isChecked
//            R.id.check_hp -> isSortByHp = isChecked
        }
    }

    private fun isSortRequired(): Boolean {
        if (isSortByAttack) return true
        if (isSortByDefense) return true
        return isSortByHp
    }

    private fun formingSortedArray() {
        sortеdArray = ArrayList<Any?>(mRepository?.getArray()!!) as List<Pokemon>
        (sortеdArray as MutableList<Pokemon>).sortWith(Comparator { pokemon1, pokemon2 ->
            val sumStatsPok1 = getSumStatsPokemon(pokemon1!!)
            val sumStatsPok2 = getSumStatsPokemon(pokemon2!!)
            if (sumStatsPok1 > sumStatsPok2) -1 else if (sumStatsPok1 < sumStatsPok2) 1 else 0
        })
    }

    private fun getSumStatsPokemon(pokemon: Pokemon): Int {
        var sumStats = 0
        val listStats: List<Stat>? = pokemon.stats
        for (stat in listStats!!) {
            when (stat.stat?.name) {
                "defense" -> if (isSortByDefense) sumStats += stat.baseStat!!
                "attack" -> if (isSortByAttack) sumStats += stat.baseStat!!
                "hp" -> if (isSortByHp) sumStats += stat.baseStat!!
            }
        }
        return sumStats
    }

    private fun getStringTypes(listTypes: List<Type>): String? {
        val stringTypes = StringBuilder("Types: " + listTypes[0].type.name)
        if (listTypes.size > 1) {
            for (i in 1 until listTypes.size) {
                stringTypes.append(", " + listTypes[i].type.name)
            }
        }
        return stringTypes.toString()
    }

    private fun getStringStats(listStats: List<Stat>): String? {
        val stringStats = StringBuilder()
        for (stat in listStats) {
            when (stat.stat?.name) {
                "defense" -> stringStats.append("Defense: " + stat.baseStat.toString() + "          ")
                "attack" -> stringStats.append("Attack: " + stat.baseStat.toString() + "          ")
                "hp" -> stringStats.append("HP: " + stat.baseStat)
            }
        }
        return stringStats.toString()
    }
}