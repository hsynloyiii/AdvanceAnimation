package com.example.advanceanimation.fragment

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.advanceanimation.R
import com.example.advanceanimation.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home,
            container,
            false
        )

        onClick()

        return binding.root
    }

    private fun onClick() {
        binding.matPropertyAnimation.navigateToDestination(
            HomeFragmentDirections
                .actionHomeFragmentToPropertyAnimFragment()
        )

        binding.matAnimateDrawable.navigateToDestination(
            HomeFragmentDirections
                .actionHomeFragmentToAnimateDrawableFragment()
        )

        binding.matRevealOrHideAnimation.navigateToDestination(
            HomeFragmentDirections
                .actionHomeFragmentToRevealOrHideViewUsingAnimationFragment()
        )

        binding.matMoveViewUsingAnimation.navigateToDestination(
            HomeFragmentDirections
                .actionHomeFragmentToMoveViewUsingAnimationFragment()
        )

        binding.matZoomAnimation.navigateToDestination(
            HomeFragmentDirections
                .actionHomeFragmentToZoomAnimationFragment()
        )
    }
}

private fun View.navigateToDestination(directions: NavDirections) =
    setOnClickListener {
        findNavController().navigate(directions, navOptions {
            anim {
                enter = R.anim.nav_default_enter_anim
                exit = R.anim.nav_default_exit_anim
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
            }
        })
    }