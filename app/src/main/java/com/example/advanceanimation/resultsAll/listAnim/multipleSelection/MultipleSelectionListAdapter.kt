package com.example.advanceanimation.resultsAll.listAnim.multipleSelection

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.ItemKeyProvider
import androidx.recyclerview.selection.Selection
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.advanceanimation.R
import com.example.advanceanimation.resultsAll.Rocket


class MultipleSelectionListAdapter :
    ListAdapter<Rocket, MultipleSelectionListAdapter.ViewHolder>(Rocket.DIFF_CALLBACK) {

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id.toLong()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder = ViewHolder(parent)

    var tracker: SelectionTracker<Long>? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        tracker?.let {
            holder.bind(
                getItem(position),
                isActivated = it.isSelected(getItem(position).id.toLong())
            )
        }
    }

    inner class ViewHolder(parent: ViewGroup) :
        RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.rocket_list_item, parent, false)
        ) {

        private val image: ImageView = itemView.findViewById(R.id.image)
        private val title: TextView = itemView.findViewById(R.id.title)
        private val linear: LinearLayout = itemView.findViewById(R.id.linearItemRecycler)

        @SuppressLint("SetTextI18n")
        fun bind(rocket: Rocket, isActivated: Boolean) {
            Glide.with(image).load(rocket.image).transform(CircleCrop()).into(image)
            title.text = rocket.name
            // Added a new boolean so if the item is select by user we change the title
//            if (isActivated) {
//                title.text = rocket.name + " is activated"
//            }
            linear.isActivated = isActivated


            tracker?.addObserver(object : SelectionTracker.SelectionObserver<Long>() {
                override fun onSelectionChanged() {
                    super.onSelectionChanged()
                    val items = tracker?.selection!!
                    Log.i("MultipleSelectionTagToDebug", "onSelectionChanged: ${getSetOfId(items)}")
                }
            })
        }

        private fun getSetOfId(selection: Selection<Long>): Set<Int> = selection.map {
            it.toInt()
        }.toSet()

        fun getItemDetails() = object : ItemDetailsLookup.ItemDetails<Long>() {
            override fun getPosition(): Int = bindingAdapterPosition

            override fun getSelectionKey(): Long = itemId
//                getItem(bindingAdapterPosition).id.toLong()

            // If we want user to activate the selection by tap gesture
            override fun inSelectionHotspot(e: MotionEvent): Boolean {
                return true
            }
        }
    }

}

/* Here we use String type of key and explanation is in the explanation class
 We have two scope =
    SCOPE_CACHED : Provides access to cached data based for items that were recently bound in the view.
    SCOPE_MAPPED  : Provide access to all data, regardless of whether it is bound to a view or not
 */
class MyItemKeyProvider(private val adapter: MultipleSelectionListAdapter) :
    ItemKeyProvider<Long>(
        SCOPE_CACHED
    ) {

    // Returns: The selection key at the given adapter position, or null.
    override fun getKey(position: Int): Long = adapter.currentList[position].id.toLong()

    // Returns: The position corresponding to the selection key, or RecyclerView.NO_POSITION if the key is unrecognized
    override fun getPosition(key: Long): Int =
        adapter.currentList.indexOfFirst { it.id.toLong() == key }

    // So the above methods give us the id and position of selected item
}


// Next to declare ItemDetailsLookup
class MyItemDetailsLookup(private val recyclerView: RecyclerView) : ItemDetailsLookup<Long>() {
    override fun getItemDetails(e: MotionEvent): ItemDetails<Long>? {
        val view = recyclerView.findChildViewUnder(e.x, e.y)
        if (view != null) {
            return (recyclerView.getChildViewHolder(view) as MultipleSelectionListAdapter.ViewHolder).getItemDetails()
        }
        return null
    }
}




