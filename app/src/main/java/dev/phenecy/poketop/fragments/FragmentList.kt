package dev.phenecy.poketop.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dev.phenecy.poketop.MainActivity
import dev.phenecy.poketop.R
import dev.phenecy.poketop.adapter.Adapter
import dev.phenecy.poketop.mvp.MainContract
import dev.phenecy.poketop.mvp.Presenter
import dev.phenecy.poketop.pokemon_characteristics.Pokemon
import kotlinx.android.synthetic.main.fragment_bottomsheet.*
import kotlinx.android.synthetic.main.fragment_list.*

public class FragmentList : Fragment(), MainContract.ViewLayer {

    private lateinit var bottomAppBar: BottomAppBar
    private lateinit var recycler: RecyclerView
    private lateinit var downloadFab: FloatingActionButton
    private lateinit var dialog: Dialog
    private lateinit var mLayoutManager: GridLayoutManager
    private lateinit var adapter: Adapter
    private var mPresenter: MainContract.PresenterLayer = Presenter()
    private var isLoading: Boolean? = null
    private var isGetItems: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        retainInstance = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val activity = (requireActivity() as AppCompatActivity)
        bottomAppBar = bottom_app_bar
        activity.setSupportActionBar(bottomAppBar)

        bottomAppBar.setNavigationOnClickListener {
            val bottomNavDrawerFragment = BottomNavigationDrawerFragment()
            bottomNavDrawerFragment.show(fragmentManager!!, bottomNavDrawerFragment.tag)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_list, container, false)
        recycler = view.findViewById(R.id.pokemon_list_rv)
        downloadFab = view.findViewById(R.id.download_fab)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recycleViewSetup()
        createDialog()
        if (savedInstanceState == null) {
            mPresenter.setLinkOnView(this)
            isLoading = false
            isGetItems = true
            mPresenter.getItems()
        } else {
            isLoading = savedInstanceState.getBoolean("isLoading")
            isGetItems = savedInstanceState.getBoolean("isGetItems")
            if (!isGetItems!!) {
                isGetItems = true
                mPresenter.getItems()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        downloadFab.setOnClickListener(View.OnClickListener {
            if (!isLoading!!) {
                isLoading = true
                clearCheckBoxes()
                createDialog()
                mPresenter.loadOtherItems()
            } else {
                Toast.makeText(activity, "Подождите, список грузится", Toast.LENGTH_LONG)
                    .show()
            }
        })
    }

    fun onCheckedChanged(buttonView: Int, isChecked: Boolean) {
        mPresenter.sortData(buttonView, isChecked)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("isLoading", isLoading!!)
        outState.putBoolean("isGetItems", isGetItems!!)
    }

    override fun attachPresenter(presenter: MainContract.PresenterLayer?) {
        mPresenter = presenter!!
        mPresenter.setLinkOnView(this)
    }

    override fun createAdapter(items: List<*>?) {
        dismissDialog()
        isGetItems = false
        adapter = Adapter(items!!)
        recycler.adapter = adapter
    }

    override fun insertItems() {
        isLoading = false
        adapter.notifyItemInserted(adapter.itemCount - 1)
    }

    override fun setOtherItems() {
        dismissDialog()
        isLoading = false
        adapter.setEnd(false)
        adapter.notifyDataSetChanged()
        recycler.smoothScrollToPosition(0)
    }

    override fun setSortedList(sortedList: List<Pokemon>) {
        adapter.changeListItems(sortedList)
        recycler.smoothScrollToPosition(0)
    }

    override fun setNewSortedList(newSortedList: List<*>?) {
        isLoading = false
        adapter.changeListItems(newSortedList!!)
    }

    override fun setUnsortedList(unsortedList: List<*>?) {
        adapter.changeListItems(unsortedList!!)
    }

    override fun allItemsWasLoaded() {
        dismissDialog()
        isLoading = false
        adapter.setEnd(true)
        adapter.notifyDataSetChanged()
        Toast.makeText(activity, "Все данные скачаны!", Toast.LENGTH_LONG).show()
    }

    override fun showError(message: String?) {
        dismissDialog()
        isLoading = false
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    override fun clickOnItem(position: Int) {
        val information = mPresenter.getInfo(position)!!
        (activity as MainActivity?)?.createFragmentInfo(information)
    }

    private fun recycleViewSetup() {
        recycler.setHasFixedSize(true)
        mLayoutManager = GridLayoutManager(activity, 2)
        recycler.layoutManager = mLayoutManager

        recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                val totalItemCount = mLayoutManager.itemCount
                val lastVisibleItem = mLayoutManager.findLastVisibleItemPosition()

                val endHasBeenReached = lastVisibleItem + 1 >= totalItemCount
                if (!isLoading!! && totalItemCount > 0 && endHasBeenReached) {
                    isLoading = true
                    mPresenter.getMoreItems()
                }
            }
        })
    }

    private fun clearCheckBoxes() {
//        BottomNavigationDrawerFragment().clearCheckBoxes()
    }


    private fun createDialog() {
        val builder =
            AlertDialog.Builder(activity).setCancelable(false)
        builder.setView(R.layout.item_progress)
        dialog = builder.create()
        dialog.show()
    }

    private fun dismissDialog() {
        dialog.dismiss()
    }


}