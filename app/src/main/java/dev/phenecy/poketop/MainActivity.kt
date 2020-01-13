package dev.phenecy.poketop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.phenecy.poketop.fragments.PokemonListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.list_container, PokemonListFragment(), null)
                .commit()
        }
    }
}
