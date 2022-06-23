package com.example.advanceanimation.resultsAll

import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.DiffUtil
import com.example.advanceanimation.R

data class Rocket(
    val id: Int,
    val name: String,
    @DrawableRes val image: Int
) {
    companion object {
        private val IMAGES = listOf(
            R.drawable.rocket_thrust1,
            R.drawable.rocket_thrust2,
            R.drawable.rocket_thrust3,
        )

        private val MORE_IMAGES = listOf(
            IMAGES,
            IMAGES,
            IMAGES,
            IMAGES,
            IMAGES,
            IMAGES,
            IMAGES,
            IMAGES,
            IMAGES,
            IMAGES,
            IMAGES,
            IMAGES,
            IMAGES,
            IMAGES,
            IMAGES,
            IMAGES,
        ).flatten()

        val ALL: List<Rocket> = MORE_IMAGES.mapIndexed { index, image ->
            Rocket(id = index + 1, name = "Rocket Thrust ${index + 1}", image = image)
        }

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Rocket>() {
            override fun areItemsTheSame(oldItem: Rocket, newItem: Rocket): Boolean =
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: Rocket, newItem: Rocket): Boolean =
                oldItem == newItem
        }
    }
}
