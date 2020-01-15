package dev.phenecy.poketop.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.CheckBox
import android.widget.Toast
import androidx.core.view.get
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dev.phenecy.poketop.R
import kotlinx.android.synthetic.main.fragment_bottomsheet.*

class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bottomsheet, container, false)

    }

//    fun clearCheckBoxes() {
//
//        filter_navigation_view.menu[R.id.check_attack].isChecked = false
//        filter_navigation_view.menu[R.id.check_defense].isChecked = false
//        filter_navigation_view.menu[R.id.check_hp].isChecked = false
//    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        filter_navigation_view.setNavigationItemSelectedListener { menuItem ->
            when (menuItem!!.itemId) {
                R.id.check_attack -> {
                    when (menuItem.isChecked) {
                        true -> {
                            menuItem.isChecked = false
                            FragmentList().onCheckedChanged(menuItem.itemId, false)
                        }
                        false -> {
                            menuItem.isChecked = true
                            FragmentList().onCheckedChanged(menuItem.itemId, true)
                        }
                    }
                }
                R.id.check_defense -> {
                    when (menuItem.isChecked) {
                        true -> {
                            menuItem.isChecked = false
                            FragmentList().onCheckedChanged(menuItem.itemId, false)
                        }
                        false -> {
                            menuItem.isChecked = true
                            FragmentList().onCheckedChanged(menuItem.itemId, true)
                        }
                    }
                }
                R.id.check_hp -> {
                    when (menuItem.isChecked) {
                        true -> {
                            menuItem.isChecked = false
                            FragmentList().onCheckedChanged(menuItem.itemId, false)
                        }
                        false -> {
                            menuItem.isChecked = true
                            FragmentList().onCheckedChanged(menuItem.itemId, true)
                        }
                    }
                }
            }
            true
        }

    }

}

fun Context.toast(message: CharSequence) {
    val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
    toast.setGravity(Gravity.BOTTOM, 0, 600)
    toast.show()
}
