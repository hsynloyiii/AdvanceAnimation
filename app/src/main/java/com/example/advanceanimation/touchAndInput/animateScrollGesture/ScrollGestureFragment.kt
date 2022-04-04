package com.example.advanceanimation.touchAndInput.animateScrollGesture

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EdgeEffect
import android.widget.ScrollView
import androidx.appcompat.resources.Compatibility
import androidx.core.widget.EdgeEffectCompat
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.advanceanimation.R
import com.example.advanceanimation.databinding.FragmentScrollGestureBinding
import com.example.advanceanimation.databinding.FragmentTouchGestureBinding
import com.example.advanceanimation.touchAndInput.animateScrollGesture.bounceEffectRecyclerview.BounceEdgeEffectFactory
import com.example.advanceanimation.touchAndInput.animateScrollGesture.bounceEffectRecyclerview.BounceEffectAdapter


class ScrollGestureFragment : Fragment() {

    private lateinit var binding: FragmentScrollGestureBinding

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScrollGestureBinding.inflate(inflater, container, false)

        onClick()

        bounceEffectRecyclerview()

        return binding.root
    }

    private fun bounceEffectRecyclerview() {
        binding.bounceEffectRecyclerView.apply {
            adapter = BounceEffectAdapter()
            edgeEffectFactory = BounceEdgeEffectFactory()
        }
//        SpringAnimation(binding.bounceEffectRecyclerView, DynamicAnimation.TRANSLATION_Y).apply {
//            spring = SpringForce(0f).apply {
//                dampingRatio = SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY
//            }
//        }
    }

    private fun onClick() {
        binding.imageBtnBack.setOnClickListener { findNavController().popBackStack() }
    }

}