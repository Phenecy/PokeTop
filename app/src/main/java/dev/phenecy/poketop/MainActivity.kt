package dev.phenecy.poketop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentTransaction
import dev.phenecy.poketop.fragments.BottomNavigationDrawerFragment
import dev.phenecy.poketop.fragments.FragmentInfo
import dev.phenecy.poketop.fragments.FragmentList
import dev.phenecy.poketop.mvp.Presenter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_list.*

class MainActivity : AppCompatActivity() {

    private var fragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        setSupportActionBar(bottom_app_bar)
        if (savedInstanceState == null) {
            val fragmentList: FragmentList = FragmentList()
            fragmentList.attachPresenter(Presenter())
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.fragment, fragmentList, "FRAGMENT_LIST")
            fragmentTransaction.commit()
        }
    }

    fun createFragmentInfo(information: Bundle) {
        val fragmentInfo = FragmentInfo()
        fragmentInfo.arguments = information

        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fragment, fragmentInfo, "FRAGMENT_INFO")
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}
