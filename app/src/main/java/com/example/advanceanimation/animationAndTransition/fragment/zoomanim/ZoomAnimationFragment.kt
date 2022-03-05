package com.example.advanceanimation.animationAndTransition.fragment.zoomanim

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.graphics.*
import android.os.Bundle
import android.util.FloatMath
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.core.animation.doOnCancel
import androidx.core.animation.doOnEnd
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.advanceanimation.R
import com.example.advanceanimation.databinding.FragmentZoomAnimationBinding
import kotlin.math.sqrt


class ZoomAnimationFragment : Fragment() {

    private lateinit var binding: FragmentZoomAnimationBinding

    // Hold the reference to the current animator, so that it can be cancelled mid-way
    private var currentAnimator: Animator? = null

    // The system short animation duration, in milliseconds
    private var shortAnimationDuration: Int = 0

    var matrix: Matrix = Matrix()
    var savedMatrix: Matrix = Matrix()

    // We can be in one of these 3 states
    val NONE = 0
    val DRAG = 1
    val ZOOM = 2
    var mode = NONE

    // Remember some things for zooming
    var start = PointF()
    var mid = PointF()
    var oldDist = 1f
    var savedItemClicked: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentZoomAnimationBinding.inflate(inflater, container, false)

        onClick()

        // Hook up clicks on imageViewWillZoom view
        binding.imageThumbnail.setOnClickListener {
            zoomImageFromThumb(binding.imageThumbnail, R.drawable.rocket_thrust1)
        }
        // retrieve and cache the system's default short animation time
        shortAnimationDuration = resources.getInteger(android.R.integer.config_shortAnimTime)



        return binding.root
    }

    private fun zoomImageFromThumb(thumbView: View, imageResId: Int) {
        // If there is animation cancel it immediately and proceed this one
        currentAnimator?.cancel()

        // Load the high-resolution zoomed-in image
        binding.imageViewExpanded.setImageResource(imageResId)

        // Calculate the starting and ending bounds for the zoomed-in image. This involves lots of math
        val startBoundInt = Rect()
        val finalBoundInt = Rect()
        val globalOffset = Point()

        /*
        The start bounds are the global visible rectangle of the thumbnail and the final bounds are the global visible rectangle of the
        container view
        Also set the container view's offset as the origin for the bounds, since that's the origin for the positioning animation properties
         */
        thumbView.getGlobalVisibleRect(startBoundInt)
        binding.containerFragmentZoomAnimation.getGlobalVisibleRect(finalBoundInt, globalOffset)
        startBoundInt.offset(-globalOffset.x, -globalOffset.y)
        finalBoundInt.offset(-globalOffset.x, -globalOffset.y)

        val startBounds = RectF(startBoundInt)
        val finalBounds = RectF(finalBoundInt)

        /*
        Adjust the start bounds to be same aspect ratio as the final bounds using the center crop technique. This prevent
        undesirable(namatlob) stretching(keshesh) during the animation.
        Also calculating the start scaling factor (the end scaling factor is always 1.0)
         */
        val startScale: Float
        if (finalBounds.width() / finalBoundInt.height() > startBounds.width() / startBounds.height()) {
            // Extend(gostaresh) start bounds horizontally
            startScale = startBounds.height() / finalBounds.height()
            val startWidth: Float = startScale * finalBounds.width()
            val deltaWidth: Float = (startWidth - startBounds.width()) / 2
            startBounds.left -= deltaWidth.toInt()
            startBounds.right += deltaWidth.toInt()
        } else {
            // Extend start bounds vertically
            startScale = startBounds.width() / finalBounds.width()
            val startHeight: Float = startScale * finalBounds.height()
            val deltaHeight: Float = (startHeight - startBounds.height()) / 2
            startBounds.left -= deltaHeight.toInt()
            startBounds.right += deltaHeight.toInt()
        }

        // Hide the thumbnail and show the zoomed-in view. When the animation begins, it will position the zoomed-in view in the place of
        // thumbnail
        thumbView.alpha = 0f
        binding.imageViewExpanded.visibility = View.VISIBLE

        /*
        Set the pivot point for SCALE_X and SCALE_Y transformation to the top-left corner of the zoomed-in view
        ( the default is the center of the view )
         */
        binding.imageViewExpanded.apply { pivotX = 0f; pivotY = 0f }

        // Construct and run the parallel animation of the four translation and scale properties(X, Y, SCALE_X, SCALE_Y)
        currentAnimator = AnimatorSet().apply {
            play(
                ObjectAnimator.ofFloat(
                    binding.imageViewExpanded,
                    View.X,
                    startBounds.left,
                    finalBounds.left
                )
            ).apply {
                with(
                    ObjectAnimator.ofFloat(
                        binding.imageViewExpanded,
                        View.Y,
                        startBounds.top,
                        finalBounds.top
                    )
                )
                with(
                    ObjectAnimator.ofFloat(
                        binding.imageViewExpanded,
                        View.SCALE_X,
                        startScale,
                        1f
                    )
                )
                with(
                    ObjectAnimator.ofFloat(
                        binding.imageViewExpanded,
                        View.SCALE_Y,
                        startScale,
                        1f
                    )
                )
            }
            duration = shortAnimationDuration.toLong()
            interpolator = DecelerateInterpolator()
            doOnEnd { currentAnimator = null }
            doOnCancel { currentAnimator = null }
            start()
        }

        /*
        Upon click the zoomed-in image, it should zoom back down to the original bounds and show the thumbnail instead of expanded image
         */
        binding.imageViewExpanded.apply expandedImage@{
            setOnClickListener {
                // Animate the four positioning/sizing properties in parallel back to their original values
                currentAnimator = AnimatorSet().apply {
                    play(
                        ObjectAnimator.ofFloat(this@expandedImage, View.X, startBounds.left)
                    ).apply {
                        with(ObjectAnimator.ofFloat(this@expandedImage, View.Y, startBounds.top))
                        with(ObjectAnimator.ofFloat(this@expandedImage, View.SCALE_X, startScale))
                        with(ObjectAnimator.ofFloat(this@expandedImage, View.SCALE_Y, startScale))
                    }
                    duration = shortAnimationDuration.toLong()
                    interpolator = DecelerateInterpolator()
                    doOnEnd {
                        binding.imageThumbnail.alpha = 1f
                        visibility = View.GONE // refer to imageViewExpanded
                        currentAnimator = null
                    }
                    doOnCancel {
                        binding.imageThumbnail.alpha = 1f
                        visibility = View.GONE // refer to imageViewExpanded
                        currentAnimator = null
                    }
                    start()
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun pinchZoom() {
        binding.imageViewExpanded.setOnTouchListener { v, event ->
            when (event.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_DOWN -> {
                    savedMatrix.set(matrix)
                    start[event.getX()] = event.getY()
                    Log.d("TAG", "mode=DRAG")
                    mode = DRAG
                }
                MotionEvent.ACTION_POINTER_DOWN -> {
                    oldDist = spacing(event)
                    Log.d("TAG", "oldDist=$oldDist")
                    if (oldDist > 10f) {
                        savedMatrix.set(matrix)
                        midPoint(mid, event)
                        mode = ZOOM
                        Log.d("TAG", "mode=ZOOM")
                    }
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP -> {
                    mode = NONE
                    Log.d("TAG", "mode=NONE")
                }
                MotionEvent.ACTION_MOVE -> if (mode === DRAG) {
                    // ...
                    matrix.set(savedMatrix)
                    matrix.postTranslate(
                        event.getX() - start.x, event.getY()
                                - start.y
                    )
                } else if (mode === ZOOM) {
                    val newDist: Float = spacing(event)
                    Log.d("TAG", "newDist=$newDist")
                    if (newDist > 10f) {
                        matrix.set(savedMatrix)
                        val scale = newDist / oldDist
                        matrix.postScale(scale, scale, mid.x, mid.y)
                    }
                }
            }

            binding.imageViewExpanded.imageMatrix = matrix
            true
        }
    }

    private fun dumpEvent(event: MotionEvent) {
        val names = arrayOf(
            "DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE",
            "POINTER_DOWN", "POINTER_UP", "7?", "8?", "9?"
        )
        val sb = StringBuilder()
        val action = event.action
        val actionCode = action and MotionEvent.ACTION_MASK
        sb.append("event ACTION_").append(names[actionCode])
        if (actionCode == MotionEvent.ACTION_POINTER_DOWN
            || actionCode == MotionEvent.ACTION_POINTER_UP
        ) {
            sb.append("(pid ").append(
                action shr MotionEvent.ACTION_POINTER_ID_SHIFT
            )
            sb.append(")")
        }
        sb.append("[")
        for (i in 0 until event.pointerCount) {
            sb.append("#").append(i)
            sb.append("(pid ").append(event.getPointerId(i))
            sb.append(")=").append(event.getX(i).toInt())
            sb.append(",").append(event.getY(i).toInt())
            if (i + 1 < event.pointerCount) sb.append(";")
        }
        sb.append("]")
        Log.d("TAG", sb.toString())
    }

    /** Determine the space between the first two fingers  */
    private fun spacing(event: MotionEvent): Float {
        val x = event.getX(0) - event.getX(1)
        val y = event.getY(0) - event.getY(1)
        return sqrt(x * x + y * y)
    }

    /** Calculate the mid point of the first two fingers  */
    private fun midPoint(point: PointF, event: MotionEvent) {
        val x = event.getX(0) + event.getX(1)
        val y = event.getY(0) + event.getY(1)
        point[x / 2] = y / 2
    }

    private fun onClick() {
        binding.imageBtnBack.setOnClickListener { findNavController().popBackStack() }
    }
}