package com.example.advanceanimation.resultsAll.listAnim.reorder

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EdgeEffect
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.advanceanimation.R
import com.example.advanceanimation.resultsAll.Rocket

class ReorderListAdapter(
    private val onItemLongClick: (holder: RecyclerView.ViewHolder) -> Unit
) : ListAdapter<Rocket, ReorderListAdapter.ViewHolder>(Rocket.DIFF_CALLBACK) {

    init {
        /*
        Returns true if this adapter publishes a unique long value that can act as a key for the item
        at a given position in the data set. If that item is relocated in the data set, the ID returned
        for that item should be the same.
         */
        setHasStableIds(true)
    }

    /*
    Return the stable ID for the item at position. If hasStableIds() would return false this method should return NO_ID.
    The default implementation of this method returns NO_ID.
     */
    override fun getItemId(position: Int): Long {
        return getItem(position).id.toLong()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder = ViewHolder(parent).apply {
        itemView.setOnLongClickListener {
            onItemLongClick(this)
            true
        }

        itemView.setOnClickListener {
            Toast.makeText(
                it.context,
                "${getItem(bindingAdapterPosition).name} clicked. Long press to start dragging",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(parent: ViewGroup) :
        RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.rocket_list_item, parent, false)
        ) {

        private val image: ImageView = itemView.findViewById(R.id.image)
        private val title: TextView = itemView.findViewById(R.id.title)

        fun bind(rocket: Rocket) {
            Glide.with(image).load(rocket.image).transform(CircleCrop()).into(image)
            title.text = rocket.name
        }
    }

}





