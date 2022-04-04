package com.example.advanceanimation.touchAndInput.animateScrollGesture.bounceEffectRecyclerview

import android.graphics.Canvas
import android.widget.EdgeEffect
import androidx.core.widget.EdgeEffectCompat
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import androidx.recyclerview.widget.RecyclerView

// The magnitude (andaze) of translation distance while the list is over-scrolled
private const val OVERSCROLL_TRANSLATION_MAGNITUDE = 1f

// The magnitude of translation distance when the list reaches the edge on fling
private const val FLING_TRANSLATION_MAGNITUDE = 0.5f

class BounceEdgeEffectFactory : RecyclerView.EdgeEffectFactory() {
    override fun createEdgeEffect(view: RecyclerView, direction: Int): EdgeEffect {
        return object : EdgeEffect(view.context) {
            // Reference to the SpringAnimation for this recyclerview used to bring the item back after over-scroll effect
            var anim: SpringAnimation? = null

            // This is called when the user is pulling the content more than the limit of the recyclerview. In this case we translate the
            // whole recyclerview
            override fun onPull(deltaDistance: Float, displacement: Float) {
                super.onPull(deltaDistance, displacement)
                handlePull(deltaDistance)
            }

            // This is called when the user is pulling the content more than the limit of the recyclerview. In this case we translate the
            // whole recyclerview
            override fun onPull(deltaDistance: Float) {
                super.onPull(deltaDistance)
                handlePull(deltaDistance)
            }

            private fun handlePull(deltaDistance: Float) {
                // Translate recyclerview with the distance
                val sign = if (direction == DIRECTION_BOTTOM) -1 else 1
                val translationYDelta =
                    sign * view.width * deltaDistance * OVERSCROLL_TRANSLATION_MAGNITUDE
                view.translationY += translationYDelta
            }

            // This is called when the user lift the finger after a pull and this the time to play our animation. The animation will move
            // the content to the rest state
            override fun onRelease() {
                super.onRelease()
                // The finger is lifted, start anim to bring translation back to the resting state
                if (view.translationY != 0f) {
                    anim = createAnim().also { it.start() }
                }
            }

            // This gets called when the user flings the content of the scrollView and reaches the edge when there is still force (niro)
            // When this happened we wanna content to move a little down and the bounce back to the final position, this can done by setting
            // start velocity to the animation
            override fun onAbsorb(velocity: Int) {
                super.onAbsorb(velocity)
                // The list has reached the edge on fling
                val sing = if (direction == DIRECTION_BOTTOM) -1 else 1
                val translationVelocity = sing * velocity * FLING_TRANSLATION_MAGNITUDE
                anim?.cancel()
                anim = createAnim().setStartVelocity(translationVelocity).also { it.start() }
            }

            private fun createAnim() = SpringAnimation(view, DynamicAnimation.TRANSLATION_Y).apply {
                spring = SpringForce(0f).apply {
                    dampingRatio = SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY
                    stiffness = SpringForce.STIFFNESS_MEDIUM
                }
            }

            override fun draw(canvas: Canvas?): Boolean {
                // don't paint the usual edge effect
                return false
            }

            override fun isFinished(): Boolean {
                return anim?.isRunning?.not() ?: true // If it isn't running return true
            }
        }
    }
}
