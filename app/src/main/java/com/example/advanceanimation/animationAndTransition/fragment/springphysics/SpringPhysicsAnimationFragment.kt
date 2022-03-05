package com.example.advanceanimation.animationAndTransition.fragment.springphysics

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.TypedValue
import android.view.*
import androidx.fragment.app.Fragment
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import androidx.navigation.fragment.findNavController
import com.example.advanceanimation.R
import com.example.advanceanimation.databinding.FragmentMoveViewUsingAnimationBinding
import com.example.advanceanimation.databinding.FragmentSpringPhysicsAnimationBinding


class SpringPhysicsAnimationFragment : Fragment() {


    private lateinit var binding: FragmentSpringPhysicsAnimationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSpringPhysicsAnimationBinding.inflate(inflater, container, false)

        onClick()

//        val anim = SpringAnimation(binding.imageViewSpring, SpringAnimation.TRANSLATION_Y)
//
//        val spring = SpringForce()
//        spring.finalPosition = 100f
//        spring.stiffness = SpringForce.STIFFNESS_LOW
//        spring.dampingRatio = SpringForce.DAMPING_RATIO_HIGH_BOUNCY
//
//        anim.spring = spring
//
//        anim.start()

        springAnimation()

        dragWithSpringAnimation()

        pinchZoomWithSpringAnimation()

        return binding.root
    }

    private fun springAnimation() {
        /*
        To build a Spring anim we need to create an instance of the SpringAnimation and provide an object, an object's property that we
            wanna to animate and an optional final spring position
        * At the time of creating a spring animation, the final position of the spring is optional.
        Though, it must be defined before starting the animation
         */
        val pixelPerSecond =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1.1f, resources.displayMetrics)
        binding.imageViewSpring.let { img ->
//            // Set up a spring anim to animate view's translationY property with final spring position at 0
//            SpringAnimation(img, DynamicAnimation.TRANSLATION_Y, -40f) to SpringAnimation(
//                img,
//                DynamicAnimation.TRANSLATION_Y,
//                0f
//            ).apply {
//                // Set the damping ration and stiffness, full explanation is in Explanation6 (start from line 7)
//                spring.dampingRatio = SpringForce.DAMPING_RATIO_HIGH_BOUNCY
//                spring.stiffness = SpringForce.STIFFNESS_LOW
//                setStartVelocity(pixelPerSecond)
//                setMinValue(0f)
//                setMaxValue(-30f)
//                start()
//            }
            // Set up a spring anim to animate view's translationY property with final spring position at 0
            SpringAnimation(img, DynamicAnimation.TRANSLATION_X, 300f).apply {
                // Set the damping ration and stiffness, full explanation is in Explanation6 (start from line 7)
                setStartVelocity(pixelPerSecond)
                spring.dampingRatio = SpringForce.DAMPING_RATIO_HIGH_BOUNCY
                spring.stiffness = SpringForce.STIFFNESS_LOW
                start()
            }
        }


        /*
        When we wanna animate multiple view we can setup OnAnimationUpdateListener to receive a callback every time there is a change
            in current view's property
         */
//        val (anim1X, anim1Y) = binding.imageViewSpring.let { img ->
//            SpringAnimation(img, DynamicAnimation.TRANSLATION_X) to
//                    SpringAnimation(img, DynamicAnimation.TRANSLATION_Y)
//        }
//
//        val (anim2X, anim2Y) = binding.imageViewSpring2.let { img2 ->
//            SpringAnimation(img2, DynamicAnimation.TRANSLATION_X) to
//                    SpringAnimation(img2, DynamicAnimation.TRANSLATION_Y)
//        }
//
//        anim1X.addUpdateListener { animation, value, velocity ->
//            anim2X.animateToFinalPosition(value)
//        }
//        anim1Y.addUpdateListener { animation, value, velocity ->
//            anim2Y.animateToFinalPosition(value)
//        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun dragWithSpringAnimation() {

        val STIFFNESS = SpringForce.STIFFNESS_MEDIUM
        val DAMPING_RATIO = SpringForce.DAMPING_RATIO_HIGH_BOUNCY
        var xAnimation: SpringAnimation? = null
        var yAnimation: SpringAnimation? = null

        binding.imageViewSpring2.apply img@{
            viewTreeObserver.addOnGlobalLayoutListener(object :
                ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    xAnimation =
                        createSpringAnimation(
                            this@img,
                            SpringAnimation.X,
                            x,
                            STIFFNESS,
                            DAMPING_RATIO
                        )
                    yAnimation =
                        createSpringAnimation(
                            this@img,
                            SpringAnimation.Y,
                            y,
                            STIFFNESS,
                            DAMPING_RATIO
                        )
                    viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            })

            var dx = 0f
            var dy = 0f
            setOnTouchListener { v, event ->
                when (event.actionMasked) {
                    MotionEvent.ACTION_DOWN -> {
                        dx = v.x - event.rawX
                        dy = v.y - event.rawY

                        xAnimation?.cancel()
                        yAnimation?.cancel()
                    }
                    MotionEvent.ACTION_MOVE -> {
                        animate()
                            .x(event.rawX + dx)
                            .y(event.rawY + dy)
                            .setDuration(0)
                            .start()
                    }
                    MotionEvent.ACTION_UP -> {
                        xAnimation?.start()
                        yAnimation?.start()
                    }
                }
                true
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun pinchZoomWithSpringAnimation() {
        val INITIAL_SCALE = 1f
        val STIFFNESS = SpringForce.STIFFNESS_MEDIUM
        val DAMPING_RATIO = SpringForce.DAMPING_RATIO_HIGH_BOUNCY
        var scaleXAnimation: SpringAnimation? = null
        var scaleYAnimation: SpringAnimation? = null
        var scaleGestureDetector: ScaleGestureDetector? = null

        scaleXAnimation = createSpringAnimation(
            v = binding.imageViewSpring3,
            property = SpringAnimation.SCALE_X,
            finalPosition = INITIAL_SCALE,
            stiffness = STIFFNESS,
            dampingRatio = DAMPING_RATIO
        )
        scaleYAnimation = createSpringAnimation(
            v = binding.imageViewSpring3,
            property = SpringAnimation.SCALE_Y,
            finalPosition = INITIAL_SCALE,
            stiffness = STIFFNESS,
            dampingRatio = DAMPING_RATIO
        )


        var scaleFactor = 1f
        scaleGestureDetector = ScaleGestureDetector(
            context,
            object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
                override fun onScale(detector: ScaleGestureDetector?): Boolean {
                    scaleFactor *= detector?.scaleFactor ?: 0f
                    binding.imageViewSpring3.scaleX *= scaleFactor
                    binding.imageViewSpring3.scaleY *= scaleFactor
                    return true
                }
            })


        binding.imageViewSpring3.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                scaleXAnimation.start()
                scaleYAnimation.start()
            } else {
                // Cancel animation so we can grab the view during previous animation
                scaleXAnimation.cancel()
                scaleYAnimation.cancel()

                // pass touch event to scale gesture detector
                scaleGestureDetector.onTouchEvent(event)
            }
            true
        }

    }

    private fun createSpringAnimation(
        v: View,
        property: DynamicAnimation.ViewProperty,
        finalPosition: Float,
        stiffness: Float,
        dampingRatio: Float
    ): SpringAnimation = SpringAnimation(v, property).apply {
        spring = SpringForce(finalPosition).apply {
            this.stiffness = stiffness
            this.dampingRatio = dampingRatio
        }
    }


    private fun onClick() {
        binding.imageBtnBack.setOnClickListener { findNavController().popBackStack() }
    }

}