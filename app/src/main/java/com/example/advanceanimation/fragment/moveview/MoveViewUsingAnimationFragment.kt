package com.example.advanceanimation.fragment.moveview

import android.animation.ObjectAnimator
import android.graphics.Path
import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.PathInterpolator
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.FlingAnimation
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
import androidx.navigation.fragment.findNavController
import com.example.advanceanimation.R
import com.example.advanceanimation.databinding.FragmentMoveViewUsingAnimationBinding
import com.example.advanceanimation.databinding.FragmentRevealOrHideViewUsingAnimationBinding

class MoveViewUsingAnimationFragment : Fragment() {

    private lateinit var binding: FragmentMoveViewUsingAnimationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoveViewUsingAnimationBinding.inflate(inflater, container, false)

        onClick()

        withObjectAnimator()

        objectAnimatorWithCurvedMotion()

        moveViewsWithFlingAnimation()

        return binding.root
    }

    private fun withObjectAnimator() {
        ObjectAnimator.ofFloat(binding.textViewImMoving, "translationX", 100f).apply {
            duration = 2000
            start()
        }
    }

    private fun objectAnimatorWithCurvedMotion() {
//        val path = Path().apply {
//            arcTo(0f, 0f, 1000f, 1000f, 270f, -180f, true)
//        }
//        val pathInterpolator = PathInterpolator(path)
//
//        ObjectAnimator.ofFloat(binding.textViewImMovingWithPathInterpolator, View.X, View.Y, path)
//            .apply {
//                interpolator = FastOutLinearInInterpolator()
//                duration = 2000
//                start()
//            }
    }

    private fun moveViewsWithFlingAnimation() {
        /*
        FlingAnimation() requires a view and the object's property we wanna animation
        The below demonstrate a scale fling. The start velocity is fixed value which set to 1 pixel per second which define the speed
            at which animation change at the beginning of the animation.
            The min value set to 0 (which our scale can have 0f at min) and max of 1.5
            The friction is 1.1 which define how quickly the velocity decreases in an animation
         */
        val pixelPerSecond: Float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
            1F, resources.displayMetrics)
        FlingAnimation(binding.textViewImMovingWithFlingAnimation, DynamicAnimation.SCALE_X).apply {
            setStartVelocity(pixelPerSecond)
            setMinValue(0f)
            setMaxValue(1.5f)
            friction = 1.1f
            start()
        }
    }

    private fun onClick() {
        binding.imageBtnBack.setOnClickListener { findNavController().popBackStack() }
    }

}