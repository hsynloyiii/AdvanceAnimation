package com.example.advanceanimation.resultsAll.listAnim.stagger

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.TimeInterpolator
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.core.view.animation.PathInterpolatorCompat
import androidx.transition.Fade
import androidx.transition.TransitionValues


// Fade-IN is handled by system but we customize it and add a slight slide-up effect to it
class StaggerTransition : Fade(IN) {
    /*
     * Incoming elements are animated using deceleration easing, which starts a transition at peak
     * velocity (the fastest point of an element’s movement) and ends at
     */
    val LINEAR_OUT_SLOW_IN: TimeInterpolator by lazy(LazyThreadSafetyMode.NONE) {
        PathInterpolatorCompat.create(0f, 0f, 0.2f, 1f)
    }

    init {
        // Duration is for single item
        duration = 150.toLong()
        interpolator = DecelerateInterpolator()
        /*
        Returns the TransitionPropagation used to calculate Animator start delays
        The TransitionPropagation specifies how the start delays are calculated so extend TransitionPropagation
            to customize start delays for Animators.
        With no TransitionPropagation, all Views will react simultaneously to the start of the transition.
        So if we wanna all views transition won't happen at the same time we use propagation
         */
        propagation = SlidePropagation().apply {
            setSide(Gravity.BOTTOM)
            // We want the last item start to Fade.IN when the first item finish animating
            setPropagationSpeed(1f)
        }
    }

    override fun createAnimator(
        sceneRoot: ViewGroup,
        startValues: TransitionValues?,
        endValues: TransitionValues?
    ): Animator? {
        val view = startValues?.view ?: endValues?.view ?: return null
        val fadeAnimator = super.createAnimator(sceneRoot, startValues, endValues) ?: return null
        return AnimatorSet().apply {
            playTogether(
                fadeAnimator,
                // We made a view to slide-up a little as it fades in
                ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, view.height * 0.5f, 0f)
            )
        }
    }

}