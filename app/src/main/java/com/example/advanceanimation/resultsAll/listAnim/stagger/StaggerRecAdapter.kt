package com.example.advanceanimation.resultsAll.listAnim.stagger

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EdgeEffect
import android.widget.ImageView
import android.widget.TextView
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.advanceanimation.R
import com.example.advanceanimation.resultsAll.Rocket

class StaggerRecAdapter : ListAdapter<Rocket, StaggerRecAdapter.ViewHolder>(Rocket.DIFF_CALLBACK) {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder = ViewHolder(parent)

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

        init {
            // If false, the ViewGroup won't transition, only its children.
            // If true, the entire ViewGroup will transition together
            (itemView as ViewGroup).isTransitionGroup = true
        }

        fun bind(rocket: Rocket) {
            Glide.with(image).load(rocket.image).transform(CircleCrop()).into(image)
            title.text = rocket.name
        }

        val translationY: SpringAnimation = SpringAnimation(itemView, SpringAnimation.TRANSLATION_Y)
            .setSpring(
                SpringForce()
                    .setFinalPosition(0f)
                    .setDampingRatio(SpringForce.DAMPING_RATIO_LOW_BOUNCY)
                    .setStiffness(SpringForce.STIFFNESS_LOW)
            )
    }


    val edgeEffectFactory = object : RecyclerView.EdgeEffectFactory() {
        override fun createEdgeEffect(view: RecyclerView, direction: Int): EdgeEffect =
            object : EdgeEffect(view.context) {
                override fun onPull(deltaDistance: Float) {
                    super.onPull(deltaDistance)
                    handlePull(deltaDistance)
                }

                override fun onPull(deltaDistance: Float, displacement: Float) {
                    super.onPull(deltaDistance, displacement)
                    handlePull(deltaDistance)
                }

                private fun handlePull(deltaDistance: Float) {
                    val sign = if (direction == DIRECTION_BOTTOM) -1 else 1
                    val translationYDelta =
                        sign * view.width * deltaDistance * OVERSCROLL_TRANSLATION_MAGNITUDE
                    view.forEachVisibleHolder { holder: ViewHolder ->
                        holder.translationY.cancel()
                        holder.itemView.translationY += translationYDelta
                    }
                }

                override fun onRelease() {
                    super.onRelease()
                    view.forEachVisibleHolder { holder: ViewHolder ->
                        holder.translationY.start()
                    }
                }

                override fun onAbsorb(velocity: Int) {
                    super.onAbsorb(velocity)
                    val sign = if (direction == DIRECTION_BOTTOM) -1 else 1
                    val translationVelocity = sign * velocity * FLING_TRANSLATION_MAGNITUDE * 0.5f
                    view.forEachVisibleHolder { holder: ViewHolder ->
                        holder.translationY
                            .setStartVelocity(translationVelocity)
                            .start()
                    }
                }
            }
    }
}

/** The magnitude of translation distance while the list is over-scrolled. */
private const val OVERSCROLL_TRANSLATION_MAGNITUDE = 0.2f

/** The magnitude of translation distance when the list reaches the edge on fling. */
private const val FLING_TRANSLATION_MAGNITUDE = 0.5f

// Runs [action] on every visible [RecyclerView.ViewHolder] in this [RecyclerView]
inline fun <reified T : RecyclerView.ViewHolder> RecyclerView.forEachVisibleHolder(
    action: (T) -> Unit
) {
    for (i in 0 until childCount) {
        action(getChildViewHolder(getChildAt(i)) as T)
    }
}





