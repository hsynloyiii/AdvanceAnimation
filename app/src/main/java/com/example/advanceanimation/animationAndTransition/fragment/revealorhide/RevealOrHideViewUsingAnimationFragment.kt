package com.example.advanceanimation.animationAndTransition.fragment.revealorhide

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import androidx.core.animation.doOnEnd
import androidx.core.graphics.PathParser
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.advanceanimation.R
import com.example.advanceanimation.databinding.FragmentAnimateDrawableBinding
import com.example.advanceanimation.databinding.FragmentRevealOrHideViewUsingAnimationBinding
import kotlinx.coroutines.delay
import kotlin.math.hypot

class RevealOrHideViewUsingAnimationFragment : Fragment() {

    private lateinit var binding: FragmentRevealOrHideViewUsingAnimationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRevealOrHideViewUsingAnimationBinding.inflate(inflater, container, false)

        onClick()

        crossFadeAnimation()

        cardFlipAnimation()

        circularRevealAnimation()

        return binding.root
    }

    private fun crossFadeAnimation() {
        /*
        For the view that is fading in set the alpha value from 0 to 1 and for the view that is fading out animate the alpha value
            from 1 to 0
        Using onAnimationEnd in ana Animator.AnimatorListener for the view that's faded out, set the visibility to GONE.
         */
        binding.textViewCrossFade.visibility = View.GONE
        val shortAnimationDuration = resources.getInteger(android.R.integer.config_shortAnimTime)

        binding.progressBarCrossFade.setOnClickListener {

            binding.textViewCrossFade.apply {
                // Set the alpha to 0 but visible so that is visible but fully transparent during the animation
                alpha = 0f
                visibility = View.VISIBLE

                // Animate the content to 1f alpha and clear any animation listener(due to we don't need)
                animate()
                    .alpha(1f)
                    .setListener(null).duration = shortAnimationDuration.toLong()
            }

            // Animate the progress to 0f alpha and after animation ends, set its visibility to GONE
            binding.progressBarCrossFade.animate()
                .alpha(0f)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        binding.progressBarCrossFade.visibility = View.GONE
                    }
                })

        }
    }

    private fun cardFlipAnimation() {
        binding.btnCardFlipAnimation.setOnClickListener {
            val action = RevealOrHideViewUsingAnimationFragmentDirections
                .actionRevealOrHideViewUsingAnimationFragmentToCardFlippedAnimatedFragment()
            findNavController().navigate(
                action,
                navOptions {
                    anim {
                        enter = R.animator.card_flip_right_in
                        exit = R.animator.card_flip_right_out
                        popEnter = R.animator.card_flip_left_in
                        popExit = R.animator.card_flip_left_out
                    }
                }
            )
        }
    }

    private fun circularRevealAnimation() {
        binding.imageViewCircularReveal.visibility = View.INVISIBLE

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            delay(1000)

            binding.imageViewCircularReveal.apply {

                // get the center for the clipping circle
                val centerX = width / 2
                val centerY = height / 2

                // get the final radius for clipping circle
                // Math.hypot calculate the square root of squares of numbers passed to it as argument (mosahebe jazr majmoe majzore adad ersal shode)
                val finalRadius = hypot(centerX.toDouble(), centerY.toDouble()).toFloat()

                // Create the animator for this view ( the start radius is 0)
                ViewAnimationUtils.createCircularReveal(
                    binding.imageViewCircularReveal,
                    centerX,
                    centerY,
                    0f,
                    finalRadius
                ).apply {
                    duration = 1000

                    // make the view visible and start the animation
                    visibility = View.VISIBLE
                    start()
                }
            }
        }


        // To hide a view with circularReveal
        binding.imageViewCircularReveal.apply {
            setOnClickListener {

                // get the center for the clipping circle
                val centerX = width / 2
                val centerY = height / 2

                // But now we get the initial radius for the clipping circle and set the final radius to 0
                val initialRadius = hypot(centerX.toDouble(), centerY.toDouble()).toFloat()

                // Create the animation (the final radius is 0)
                ViewAnimationUtils.createCircularReveal(
                    binding.imageViewCircularReveal,
                    centerX,
                    centerY,
                    initialRadius,
                    0f
                ).apply {
                    duration = 1000

                    // make the view invisible when animation is done
                    doOnEnd {
                        visibility = View.INVISIBLE
                    }
                    start()
                }
            }
        }
    }

    private fun onClick() {
        binding.imageBtnBack.setOnClickListener { findNavController().popBackStack() }
    }
}