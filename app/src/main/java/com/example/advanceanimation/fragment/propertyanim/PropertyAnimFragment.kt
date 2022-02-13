package com.example.advanceanimation.fragment.propertyanim

import android.animation.*
import android.graphics.drawable.AnimatedStateListDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnticipateOvershootInterpolator
import android.view.animation.OvershootInterpolator
import androidx.core.animation.addListener
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnRepeat
import androidx.core.animation.doOnStart
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.advanceanimation.R
import com.example.advanceanimation.databinding.FragmentHomeBinding
import com.example.advanceanimation.databinding.FragmentPropertyAnimBinding


class PropertyAnimFragment : Fragment() {


    private lateinit var binding: FragmentPropertyAnimBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_property_anim,
            container,
            false
        )

        onClick()

        valueAnimator()

        objectAnimator()

        animateLayoutChanges()

        stateListAnimator()

        return binding.root
    }

    private fun valueAnimator() {
        /*
            The big explanation is on Explanation class
            The ValueAnimator let us animate values of some type for the duration of an animation by specifying a set of int, float or
            color values to animate through.
            We obtain a ValueAnimator by calling one of its factory methods : ofInt(), ofFloat(), or ofObject() ( for TypeEvaluator() )
            In the code below the ValueAnimator start calculating the values of the animation, between 0 and 200 for a duration of
            1000 ms, when the start() method runs
         */
        ValueAnimator.ofFloat(0f, 200f).apply {
            duration = 1000
            // we can use the values of animation by adding AnimatorUpdateListener and in onAnimateUpdate() method we can access the updated
            // animation value and use it in a property of one of our views
            addUpdateListener {
                binding.textValueAnimator.translationX = it.animatedValue as Float
            }
            start()
        }
    }

    /*
    If we wanna animate a type that is unknown to the Android system, we can create our own evaluator by implementing the TypeEvaluator interface
    There is only one method to implement which is evaluate(). This allows the animator that we are using to return an appropriate value for our animated property
    Here is how FloatEvaluator works :
     */
    private class FloatEvaluator2 : TypeEvaluator<Any> {
        override fun evaluate(fraction: Float, startValue: Any?, endValue: Any?): Any {
            return (startValue as Number).toFloat().let { startFloat ->
                startFloat + fraction * ((endValue as Number).toFloat() - startFloat)
            }
        }
    }


    private fun objectAnimator() {
        /*
        The ability to animate a named property of a target object
        In here there is no need to implement ValueAnimator.AnimatorUpdateListener, because the animated property updates automatically
        Instantiating is similar to ValueAnimator but we also specify the object and the name of that object's property as string

        As ObjectAnimator auto updates the property during animation, the property should have setter method to change its value
        * If we specify only one value for vararg value parameter, it is assume to be ending value of the animation. Therefore, the object
            property should have getter function that is used to obtain the starting value of animation
        * Depending on what property or object we are animating, we might need to call the invalidate() method on a View to force the screen
            to redraw itself with the updated animated values. We do this in the onAnimationUpdate() callback. For example, animating the
            color property of a Drawable object only causes update to the screen when that object redraw itself.
            All the property setter on View, such as setAlpha() and setTranslationX() invalidate the View property, so we don't need to call
            invalidate() when calling these methods
         */
        ObjectAnimator
            .ofFloat(binding.textObjectAnimator, "translationX", 0f, 200f).apply {
                duration = 1000
                start()
            }
    }

    private fun animatorSet() {
        /*
        We can play set of Animator objects together into AnimatorSet, so we can specify whether to start animations simultaneously sequentially
            or after s specified delay. ( We can also nest AnimatorSet objects withing each other )
        The following code snippet play the following Animator objects in the following manner :
            1. Plays bounceAnim
            2. Play squashAnim1, squashAnim2, stretchAnim1 and stretchAnim2 at the same time
            3. Plays bounceBackAnim
            4. plays fadeAnim
            =>
        val bouncer = AnimatorSet().apply {
            play(bounceAnim).before(squashAnim1)
            play(squashAnim1).with(squashAnim2)
            play(squashAnim1).with(stretchAnim1)
            play(squashAnim1).with(stretchAnim2)
            play(bounceBackAnim).after(stretchAnim2)
        }
        val fadeAnim = ObjectAnimator.ofFloat(newBall, "alpha", 1f, 0f).apply {
            duration = 250
        }
        AnimatorSet().apply {
            play(bouncer).before(fadeAnim)
            start()
        }
         */
    }

    private fun animateLayoutChanges() {
        binding.textValueAnimator.setOnClickListener {
            binding.textValueAnimator.visibility = View.GONE
        }
        binding.textObjectAnimator.setOnClickListener {
            binding.textValueAnimator.visibility = View.VISIBLE
        }


        // If we wanna support height/width Views changes we should enable the Changing constants
//        binding.linearViewGroup.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)

        /*
        We can change the animation of LayoutTransition
        As LayoutTransition get Animator object we should create ObjectAnimator and set the animation for different constants( describe in Explanation)
        One anim for APPEARING and one for DISAPPEARING
         */
        val scaleDown = ObjectAnimator.ofPropertyValuesHolder(
            binding.linearViewGroup,
            PropertyValuesHolder.ofFloat("scaleX", 1f, 0f),
            PropertyValuesHolder.ofFloat("scaleY", 1f, 0f)
        ).apply {
            duration = 300
            interpolator = OvershootInterpolator()
        }

        val scaleUp = ObjectAnimator.ofPropertyValuesHolder(
            binding.linearViewGroup,
            PropertyValuesHolder.ofFloat("scaleX", 0f, 1f),
            PropertyValuesHolder.ofFloat("scaleY", 0f, 1f)
        ).apply {
            duration = 300
            startDelay = 300
            interpolator = OvershootInterpolator()
        }

        binding.linearViewGroup.layoutTransition = LayoutTransition().apply {
            setAnimator(LayoutTransition.APPEARING, scaleUp)
            setAnimator(LayoutTransition.DISAPPEARING, scaleDown)
            enableTransitionType(LayoutTransition.CHANGING)
        }

    }

    private fun stateListAnimator() {
//         We can add the xml programmatically
//        binding.btnStateListAnimator.stateListAnimator =
//            AnimatorInflater.loadStateListAnimator(requireContext(), R.xml.animate_scale)

        // Here we add and create it programmatically
        val scaleDownX =
            ObjectAnimator.ofFloat(
                binding.btnStateListAnimator,
                "scaleX",
                1f, 0.92f
            )
        val scaleDownY =
            ObjectAnimator.ofFloat(
                binding.btnStateListAnimator,
                "scaleY",
                1f, 0.92f
            )
        val animSetScaleDown = AnimatorSet().apply {
            playTogether(scaleDownX, scaleDownY)
        }


        val scaleUpX =
            ObjectAnimator.ofFloat(
                binding.btnStateListAnimator,
                "scaleX",
                0.92f, 1f
            )
        val scaleUpY =
            ObjectAnimator.ofFloat(
                binding.btnStateListAnimator,
                "scaleY",
                0.92f, 1f
            )
        val animSetScaleUp = AnimatorSet().apply {
            playTogether(scaleUpX, scaleUpY)
        }

        // Instead of creating animator set we can use one ObjectAnimator
        val scaleDownXY = ObjectAnimator.ofPropertyValuesHolder(
            binding.btnStateListAnimator,
            PropertyValuesHolder.ofFloat("scaleX", 0.92f),
            PropertyValuesHolder.ofFloat("scaleY", 0.92f)
        )
        val scaleUpXY = ObjectAnimator.ofPropertyValuesHolder(
            binding.btnStateListAnimator,
            PropertyValuesHolder.ofFloat("scaleX", 1f),
            PropertyValuesHolder.ofFloat("scaleY", 1f)
        )

        val stateListAnimScale = StateListAnimator().apply {
            addState(
                intArrayOf(android.R.attr.state_pressed),
                scaleDownXY
            )
            // intArrayOf(0) means default or we can use negative that attr -android.R.attr.state_xxx or just use empty intArrayOf()
            addState(
                intArrayOf(
//                    0, // or
//                    -android.R.attr.state_pressed // or not even this use nothing
                ),
                scaleUpXY
            )
        }

        binding.btnStateListAnimator.stateListAnimator = stateListAnimScale
    }

    private fun onClick() {
        binding.imageBtnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}