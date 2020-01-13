package dev.phenecy.poketop.mvp

import android.os.Bundle
import dev.phenecy.poketop.pokemon_characteristics.Pokemon

public interface MainContract {

    interface ViewLayer {

        fun attachPresenter(presenter: MainContract.PresenterLayer)

        fun createAdapter(items: List<*>?)

        fun insertItems()

        fun setOtherItems()

        fun setSortedList(sortedList: List<*>?)

        fun setNewSortedList(newSortedList: List<*>?)

        fun setUnsortedList(unsortedList: List<*>?)

        fun allItemsWasLoaded()

        fun clickOnItem(position: Int)

        fun showError(message: String?)
    }

    interface PresenterLayer {
        fun getItems()

        fun getMoreItems()

        fun loadOtherItems()

        fun dataIsPrepared()

        fun dataIsLoaded(data: List<*>?)

        fun isEmpty()

        fun getInfo(index: Int): Bundle?

        fun sortData(checkBoxId: Int, isChecked: Boolean)

        fun setLinkOnView(view: MainContract.ViewLayer?)
    }

    interface ModelLayer {
        fun getData()

        fun loadMoreData()

        fun loadOtherData()

        fun saveData(data: List<*>?)

        fun getArray(): List<*>?

        fun clearArray()

        fun getSizeArray(): Int

        fun getCountPokemons(): Int

        fun getPokemonInfo(index: Int): Pokemon?
    }
}