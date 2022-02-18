package com.example.advanceanimation.fragment.animatedrawable

import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.VectorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.PathParser
import androidx.core.graphics.drawable.DrawableCompat
import androidx.navigation.fragment.findNavController
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.example.advanceanimation.R
import com.example.advanceanimation.databinding.FragmentAnimateDrawableBinding


class AnimateDrawableFragment : Fragment() {

    private lateinit var binding: FragmentAnimateDrawableBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAnimateDrawableBinding.inflate(inflater, container, false)

        onClick()

        animationDrawable()

        animatedVectorDrawable()

        return binding.root
    }

    private fun animationDrawable() {
        // Handle with XML
//        var rocketAnimation: AnimationDrawable
//        binding.imageAnimationDrawable.apply {
//            setBackgroundResource(R.drawable.rocket_thrust)
//            rocketAnimation = background as AnimationDrawable
//
//            setOnClickListener {
//                rocketAnimation.start()
//            }
//        }

        // Handle programmatically
        val animDrawable = AnimationDrawable().apply {
            isOneShot = true
            addFrame(ResourcesCompat.getDrawable(resources, R.drawable.rocket_thrust1, null)!!, 200)
            addFrame(ResourcesCompat.getDrawable(resources, R.drawable.rocket_thrust2, null)!!, 200)
            addFrame(ResourcesCompat.getDrawable(resources, R.drawable.rocket_thrust3, null)!!, 200)
        }
        binding.imageAnimationDrawable.apply {
            setImageDrawable(animDrawable)

            setOnClickListener {
                animDrawable.start()
            }
        }
    }

    private fun animatedVectorDrawable() {
        // With XML
        binding.imageAnimatorVectorDrawable.apply {
            setImageResource(R.drawable.animator_vector_drawable) // it can be setBackgroundResource
            val animatedVectorDrawable = drawable as AnimatedVectorDrawable // it can be background as ...

            setOnClickListener {
                animatedVectorDrawable.start()

                // With this code we can change the drawable color
                DrawableCompat.setTint(binding.imageAnimatorVectorDrawable.drawable, ContextCompat.getColor(requireContext(), R.color.gray))
            }
        }

        // No need to implement them programmatically because it takes too much time to do.
    }

    private fun onClick() {
        binding.imageBtnBack.setOnClickListener { findNavController().popBackStack() }
    }
}