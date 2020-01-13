package dev.phenecy.poketop.mvp

import android.os.Bundle
import android.util.Log
import dev.phenecy.poketop.R
import dev.phenecy.poketop.pokemon_characteristics.Pokemon
import dev.phenecy.poketop.pokemon_characteristics.Stat
import dev.phenecy.poketop.pokemon_characteristics.Type
import java.util.*
import java.util.Collections.*

public class Presenter : MainContract.PresenterLayer {

    private var mView: MainContract.ViewLayer? = null
    private val mRepository: MainContract.ModelLayer? = null
    private var sortеdArray: List<Pokemon>? = null
    private var isGetMore = false
    private var isLoadOther = false
    private var isSortByAttack = false
    private var isSortByDefense = false
    private var isSortByHp = false

    public Presenter()
    { this.mRepository = Repository(this) }

    override fun getItems() {
        if (isSortRequired()) mView!!.createAdapter(sortеdArray) else mRepository!!.getData()
    }

    override fun getMoreItems() {
        isGetMore = true
        mRepository!!.loadMoreData()
    }

    override fun loadOtherItems() {
        isLoadOther = true
        mRepository!!.loadOtherData()
    }

    override fun dataIsPrepared() {
        mView!!.createAdapter(mRepository!!.getArray())
    }

    override fun dataIsLoaded(data: List<*>?) {
        Log.v("RetrofitCheck", "dataIsLoaded")
        if (data != null) {
            saveData(data)
        }
        if (isGetMore) {
            isGetMore = false
            if (isSortRequired()) {
                formingSortedArray()
                mView!!.setNewSortedList(sortеdArray)
            } else {
                mView!!.insertItems()
            }
        } else {
            if (isLoadOther) {
                Log.v("RetrofitCheck", "isLoadOther$isLoadOther")
                isLoadOther = false
                mView!!.setOtherItems()
            } else {
                mView!!.createAdapter(mRepository!!.getArray())
            }
        }
    }

    override fun getInfo(index: Int): Bundle? {
        val information = Bundle()
        val pokemon: Pokemon?
        pokemon = if (isSortRequired()) sortеdArray!![index] else mRepository!!.getPokemonInfo(index)

        val listTypes: List<Type>? = pokemon?.types
        val listStats: List<Stat>? = pokemon?.stats

        information.putString("name", pokemon?.name)
        information.putString("types", listTypes?.let { getStringTypes(it) })
        information.putString("height", "Height: " + (pokemon?.height ?:))
        information.putString("weight", "Weight: " + (pokemon?.weight ?:))
        information.putString("stats", listStats?.let { getStringStats(it) })
        information.putString("urlPicture", pokemon?.sprites?.picture)
        return information
    }

    override fun sortData(checkBoxId: Int, isChecked: Boolean) {
        changeSortMode(checkBoxId, isChecked)
        if (mRepository!!.getSizeArray() !== 0) {
            if (isSortRequired()) {
                formingSortedArray()
                mView!!.setSortedList(sortеdArray)
            } else {
                mView!!.setUnsortedList(mRepository!!.getArray())
            }
        }
    }

    override fun isEmpty() {
        isGetMore = false
        isLoadOther = false
        if (isLoadOther || mRepository!!.getSizeArray() < mRepository.getCountPokemons()) mView!!
                .showError("Failed to get data from server.") else mView!!.allItemsWasLoaded()
    }

    override fun setLinkOnView(view: MainContract.ViewLayer?) {
        mView = view
    }

    private fun saveData(data: List<*>) {
        Log.v("RetrofitCheck", "Save")
        if (isLoadOther) mRepository!!.clearArray()
        mRepository!!.saveData(data)
    }

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
        return if (isSortByHp) true else false
    }

    private fun formingSortedArray() {
        sortеdArray = object: List<Pokemon>
        sort(sortеdArray, object : Comparator<Pokemon?> {
            fun compare(pok1: Pokemon, pok2: Pokemon): Int {
                val sumStatsPok1 = getSumStatsPokemon(pok1)
                val sumStatsPok2 = getSumStatsPokemon(pok2)
                return if (sumStatsPok1 > sumStatsPok2) -1 else if (sumStatsPok1 < sumStatsPok2) 1 else 0
            }

            override fun compare(o1: Pokemon?, o2: Pokemon?): Int {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }

    private fun getSumStatsPokemon(pokemon: Pokemon): Int {
        var sumStats = 0
        val listStats: List<Stat>? = pokemon.stats
        if (listStats != null) {
            for (stat in listStats) {
                when (stat.stat?.name) {
                    "defense" -> if (isSortByDefense) sumStats += stat.baseStat
                    "attack" -> if (isSortByAttack) sumStats += stat.getBaseStat()
                    "hp" -> if (isSortByHp) sumStats += stat.getBaseStat()
                }
            }
        }
        return sumStats
    }

    private fun getStringTypes(listTypes: List<Type>): String? {
        val stringTypes = StringBuilder("Types: " + listTypes[0].getType().getName())
        if (listTypes.size > 1) {
            for (i in 1 until listTypes.size) {
                stringTypes.append(", " + listTypes[i].getType().getName())
            }
        }
        return stringTypes.toString()
    }

    private fun getStringStats(listStats: List<Stat>): String? {
        val stringStats = StringBuilder()
        for (stat in listStats) {
            when (stat.getStat().getName()) {
                "defense" -> stringStats.append("Defense: " + stat.getBaseStat().toString() + "          ")
                "attack" -> stringStats.append("Attack: " + stat.getBaseStat().toString() + "          ")
                "hp" -> stringStats.append("HP: " + stat.getBaseStat())
            }
        }
        return stringStats.toString()
    }

}