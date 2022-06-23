package com.example.advanceanimation.animationAndTransition.fragment.customTransition

import android.animation.Animator
import android.transition.Transition
import android.transition.TransitionValues
import android.view.ViewGroup
import androidx.core.view.drawToBitmap

class CustomTransition : Transition() {

    /*
    Define a key for storing a property value in TransitionValues.values with the syntax to avoid collisions
     */
    private val PROPNAME_BACKGROUND =
        "com.example.advanceanimation.animationAndTransition.customTransition:CustomTransition:background"

    override fun captureStartValues(transitionValues: TransitionValues) {
        captureValues(transitionValues)
    }

    override fun captureEndValues(transitionValues: TransitionValues) {
        captureValues(transitionValues)
    }

    // For the view in transitionValues.view, get the values we want and put them in transitionValues.value
    private fun captureValues(transitionValues: TransitionValues) {
        // Get the reference to View
        val view = transitionValues.view
        // Store its background property in the values map
        transitionValues.values[PROPNAME_BACKGROUND] = view.drawToBitmap()
    }

    // use the view property values we captured to create an Animator object
    override fun createAnimator(
        sceneRoot: ViewGroup?,
        startValues: TransitionValues?,
        endValues: TransitionValues?
    ): Animator {
        return super.createAnimator(sceneRoot, startValues, endValues)
    }
}