package dev.phenecy.poketop.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import dev.phenecy.poketop.R
import dev.phenecy.poketop.pokemon_characteristics.Pokemon

public class Adapter(items: List<>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_ITEM: Int = 1
    private val VIEW_PROGRESS: Int = 0
    private var isEnd: Boolean = false
    private var arrayItems: List<Any> = items

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        val holder: RecyclerView.ViewHolder
        if (viewType == VIEW_ITEM) {
            view = LayoutInflater.from(viewGroup.context).inflate(R.layout.pokemon_list_item, viewGroup, false)
            holder = ItemViewHolder(view)
        } else {
            view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_progress, viewGroup, false)
            holder = ProgressViewHolder(view)
        }

        return holder
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, i: Int) {
        if (viewHolder instanceOf MenuView.ItemViewHolder) {
            ((ItemViewHolder).bind((arrayItems.get(i) as Pokemon))
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position != arrayItems.size) {
            return VIEW_ITEM
        } else {
            return VIEW_PROGRESS
        }
    }

    override fun getItemCount(): Int {
        if (isEnd) {
            return arrayItems.size
        } else {
            if (arrayItems.size == 0) {
                return 0
            } else {
                return arrayItems.size + 1
            }
        }
    }

    public fun changeListItems(newList: List<Any>)
    {
        arrayItems = newList
        notifyDataSetChanged()
    }

    public fun setEnd(isEnd: Boolean) {
        this.isEnd = isEnd
    }
}