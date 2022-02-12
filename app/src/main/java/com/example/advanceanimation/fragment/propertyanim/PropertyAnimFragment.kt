package com.example.advanceanimation.fragment.propertyanim

import android.animation.ObjectAnimator
import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnticipateOvershootInterpolator
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

    private fun onClick() {
        binding.imageBtnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}