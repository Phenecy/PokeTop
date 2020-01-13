package dev.phenecy.poketop.mvp

import android.os.AsyncTask
import dev.phenecy.poketop.api.RetrofitService
import dev.phenecy.poketop.pokemon_characteristics.Pokemon
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import org.json.JSONTokener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.lang.ref.WeakReference
import java.util.*

public class Repository : MainContract.ModelLayer {

    private var mPresenter: WeakReference<MainContract.PresenterLayer>? = null
    private var arrayPokemons: MutableList<Pokemon>? = null
    private val size = 30
    private var startIndex = 1
    private var lastIndex = startIndex + size
    private var totalCountPokemons = size

    fun Repository(presenter: MainContract.PresenterLayer) {
        mPresenter = WeakReference<MainContract.PresenterLayer>(presenter)
        arrayPokemons = ArrayList<Pokemon>()
    }

    override fun getData() {
        if (getSizeArray() == 0) {
            getTotalCountPokemons()
            DowloaderData().execute()
        } else {
            mPresenter!!.get()?.dataIsPrepared()
        }
    }

    override fun loadMoreData() {
        if (startIndex < totalCountPokemons) {
            startIndex = if (lastIndex < totalCountPokemons) lastIndex else totalCountPokemons
            lastIndex = startIndex + size
            DowloaderData().execute()
        } else {
            mPresenter!!.get()?.isEmpty()
        }
    }

    override fun loadOtherData() {
        startIndex = (Math.random() * totalCountPokemons - 30 + 1).toInt()
        lastIndex = startIndex + size
        DowloaderData().execute()
    }

    override fun saveData(data: List<*>?) {
        val addAll = arrayPokemons!!.addAll(data!!)
    }

    override fun clearArray() {
        arrayPokemons!!.clear()
    }

    override fun getPokemonInfo(index: Int): Pokemon? {
        return arrayPokemons!![index]
    }

    override fun getArray(): List<*>? {
        return arrayPokemons
    }

    override fun getSizeArray(): Int {
        return arrayPokemons!!.size
    }

    override fun getCountPokemons(): Int {
        return totalCountPokemons
    }

    private fun getDataFromServer(): List<Pokemon>? {
        val downloadedArray: MutableList<Pokemon> = ArrayList<Pokemon>(size)
        val retrofit = Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val api: RetrofitService = retrofit.create(RetrofitService::class.java)
        for (i in startIndex until lastIndex) {
            val call: Call<Pokemon> = api.getPokemon(i)
            try {
                val response: Response<Pokemon> = call.execute()
                val pokemon: Pokemon? = response.body()
                if (pokemon != null) downloadedArray.add(pokemon)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return downloadedArray
    }

    /**
     * Getting total count pokemons from server
     */
    private fun getTotalCountPokemons() {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .build()
        val api: RetrofitService = retrofit.create(RetrofitService::class.java)
        val call: Call<ResponseBody> = api.getCountPokemons()
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    val json = JSONTokener(response.body()!!.string())
                    val data = json.nextValue() as JSONObject
                    totalCountPokemons = data.getInt("count")
                } catch (e: IOException) {
                    e.printStackTrace()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }


    /**
     * Downloader data in a background thread
     */
    internal class DowloaderData : AsyncTask<Void?, List<Pokemon?>?, List<Pokemon>>() {
        protected override fun doInBackground(vararg voids: Void): List<Pokemon> {
            return getDataFromServer()
        }

        override fun onPostExecute(pokemonList: List<Pokemon>) {
            super.onPostExecute(pokemonList)
            if (pokemonList.size != 0) {
                mPresenter.get().dataIsLoaded(pokemonList)
            } else {
                mPresenter.get().isEmpty()
            }
        }
    }
}

private fun RetrofitService.getCountPokemons(): Call<ResponseBody> {

}
