package com.example.advanceanimation.fragment.propertyanim

import android.animation.ValueAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        /*
            The big explanation is on Explanation class
            The ValueAnimator let us animate values of some type for the duration of an animation by specifying a set of int, float or
            color values to animate through.
            We obtain a ValueAnimator by calling one of its factory methods : ofInt(), ofFloat(), or ofObject() ( for TypeEvaluator() )
            In the code below the ValueAnimator start calculating the values of the animation, between 0 and 100 for a duration of
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

        return binding.root
    }

    private fun onClick() {
        binding.imageBtnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}