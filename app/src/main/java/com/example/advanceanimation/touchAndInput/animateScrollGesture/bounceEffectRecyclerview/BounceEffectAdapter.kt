package com.example.advanceanimation.touchAndInput.animateScrollGesture.bounceEffectRecyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.advanceanimation.databinding.ItemRecyclerviewBounceEffectBinding

class BounceEffectAdapter : RecyclerView.Adapter<BounceEffectAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemRecyclerviewBounceEffectBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder = ViewHolder(
        ItemRecyclerviewBounceEffectBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.item = "Item $position"
    }

    override fun getItemCount(): Int = 15
}