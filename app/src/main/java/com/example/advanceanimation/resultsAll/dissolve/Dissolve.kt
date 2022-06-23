package com.example.advanceanimation.resultsAll.dissolve

import android.animation.Animator
import android.animation.ObjectAnimator
import android.graphics.Bitmap
import android.transition.Transition
import android.transition.TransitionValues
import android.view.ViewGroup
import androidx.core.animation.doOnEnd
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.drawToBitmap

class Dissolve: Transition() {

    companion object {
        private const val PROPERTY_NAME_BITMAP = "com.example.advanceanimation.resultsAll.dissolve:BITMAP"
    }

    override fun captureStartValues(transitionValues: TransitionValues) {
        captureValues(transitionValues)
    }

    override fun captureEndValues(transitionValues: TransitionValues) {
        captureValues(transitionValues)
    }

    private fun captureValues(transitionValues: TransitionValues) {
        // Store the current appearance of the view as bitmap
        transitionValues.values[PROPERTY_NAME_BITMAP] = transitionValues.view.drawToBitmap()
    }

    override fun createAnimator(
        sceneRoot: ViewGroup?,
        startValues: TransitionValues?,
        endValues: TransitionValues?
    ): Animator? {
        if (startValues == null || endValues == null)
            return null

        val startBitmap = startValues.values[PROPERTY_NAME_BITMAP] as Bitmap
        val endBitmap = endValues.values[PROPERTY_NAME_BITMAP] as Bitmap

        // No need to animate if start and end bitmap are identical
        if (startBitmap.sameAs(endBitmap))
            return null

        val view = endValues.view
        val startDrawable = startBitmap.toDrawable(view.resources).apply {
            setBounds(0, 0, startBitmap.width, startBitmap.height)
        }

        // Use ViewOverlay to show the start bitmap on top of the view that is currently showing the end state
        // An overlay is an extra layer that sits on top of the view which drawn after all other content in that view
        // This allow us to overlap the start and end states during the animation
        val overlay = view.overlay
        overlay.add(startDrawable)

        // Fade out the start bitmap
        return ObjectAnimator
            .ofInt(startDrawable, "alpha", 255, 0).apply {
                doOnEnd {
                    // Remove the start state from the overlay when the animation is over
                    // The drawable is completely transparent at this point, but we don't want to leave it there
                    overlay.remove(startDrawable)
                }
            }
    }

}