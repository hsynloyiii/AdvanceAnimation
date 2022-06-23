package com.example.advanceanimation.resultsAll.scaleWithZoom

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.graphics.Point
import android.graphics.Rect
import android.graphics.RectF
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.*
import android.view.ViewGroup.MarginLayoutParams
import android.view.animation.DecelerateInterpolator
import androidx.core.animation.doOnCancel
import androidx.core.animation.doOnEnd
import androidx.core.view.GestureDetectorCompat
import androidx.fragment.app.Fragment
import com.example.advanceanimation.R
import com.example.advanceanimation.databinding.FragmentScaleAndZoomBinding


class ScaleAndZoomFragment : Fragment() {

    private lateinit var binding: FragmentScaleAndZoomBinding

    private var currentAnimator: Animator? = null
    private var shortAnimationDuration: Int = 0
    private var mediumAnimationDuration: Int = 0

    private var startBounds: RectF = RectF()
    private var finalBounds: RectF = RectF()
    private var startScale: Float = 0f

    private var imageExpandedGestureDetectorCompat: GestureDetectorCompat? = null

    private var imageExpandedScaleGestureDetector: ScaleGestureDetector? = null

    private var isEnabledToGesturing = true

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScaleAndZoomBinding.inflate(inflater, container, false)

        binding.imageViewThumb.setOnClickListener {
            zoomImageFromThumb()
        }
        shortAnimationDuration = resources.getInteger(android.R.integer.config_shortAnimTime)
        mediumAnimationDuration = resources.getInteger(android.R.integer.config_mediumAnimTime)

        imageExpandedGestureDetectors()

        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun zoomImageFromThumb() {
        currentAnimator?.cancel()

        binding.imageViewExpanded.setImageResource(R.drawable.rocket_thrust2)

        // Calculate the start/final bounds for zoomed-in image
        val startBoundInt = Rect() // Global visible rect for thumb
        val finalBoundInt = Rect() // Global visible rect for container
        val globalOffset = Point()

        binding.imageViewThumb.getGlobalVisibleRect(startBoundInt)
        binding.containerFragmentScaleAndZoom.getGlobalVisibleRect(finalBoundInt, globalOffset)
        startBoundInt.offset(-globalOffset.x, -globalOffset.y)
        finalBoundInt.offset(-globalOffset.x, -globalOffset.y)

        startBounds = RectF(startBoundInt)
        finalBounds = RectF(finalBoundInt)

        // Calculate the start scaling factor (the end is always 1.0)
//        val startScale: Float

        if (finalBounds.width() / finalBounds.height() > startBounds.width() / startBounds.height()) {
            // Extend start bound horizontally
            startScale = startBounds.height() / finalBounds.height()
            val startWidth: Float = startScale * finalBounds.width()
            val deltaWidth: Float = (startWidth - startBounds.width()) / 2
            startBounds.left -= deltaWidth.toInt()
            startBounds.right += deltaWidth.toInt()
        } else {
            // Extend start bounds vertically
            startScale = startBounds.width() / finalBounds.width()
            val startHeight = startScale * finalBounds.height()
            val deltaHeight = (startHeight - startBounds.height()) / 2f
            startBounds.top -= deltaHeight.toInt()
            startBounds.bottom += deltaHeight.toInt()
        }

        // Hide the thumb and show the zoomed-in view, when anim begins
        binding.imageViewThumb.alpha = 0f
        binding.imageViewExpanded.visibility = View.VISIBLE
        binding.viewExpandedBackground.visibility = View.VISIBLE

        binding.imageViewExpanded.apply { pivotX = 0f; pivotY = 0f }

        // Run the parallel animation of four translation and scale properties
        currentAnimator = AnimatorSet().apply {
            playTogether(
                ObjectAnimator.ofFloat(
                    binding.imageViewExpanded,
                    View.X,
                    startBounds.left,
                    finalBounds.left
                ),
                ObjectAnimator.ofFloat(
                    binding.imageViewExpanded,
                    View.Y,
                    startBounds.top,
                    finalBounds.top
                ),
                ObjectAnimator.ofFloat(
                    binding.imageViewExpanded,
                    View.SCALE_X,
                    startScale,
                    1f
                ),
                ObjectAnimator.ofFloat(
                    binding.imageViewExpanded,
                    View.SCALE_Y,
                    startScale,
                    1f
                ),
                ObjectAnimator.ofFloat(
                    binding.viewExpandedBackground,
                    View.ALPHA,
                    0f,
                    0.9f
                )
            )
            duration = shortAnimationDuration.toLong()
            interpolator = DecelerateInterpolator()
            doOnEnd { currentAnimator = null }
            doOnCancel { currentAnimator = null }
            start()
        }

        var mLastTouchY = 0f
        var mFirstObjectY = 0f

        var mLastTouchX = 0f
        var mFirstObjectX = 0f
        var mLastTranslation = 0f
        var pointerID = 1000
        binding.imageViewExpanded.setOnTouchListener { v, event ->
            if (isEnabledToGesturing) {
                when (event.actionMasked) {
                    MotionEvent.ACTION_DOWN -> {
//                        Log.i(
//                            "ScaleAndZoom",
//                            "rawY = ${event.rawY} , y with pointer = ${event.getY(event.actionIndex)}, y = ${event.y}"
//                        )
                        mLastTouchY = event.rawY
                        mFirstObjectY = binding.imageViewExpanded.y
                        pointerID = event.getPointerId(0)
                    }
                    MotionEvent.ACTION_MOVE -> {
//                        Log.i(
//                            "ScaleAndZoom",
//                            "rawY = ${event.rawY} , firstY = $mLastTouchY , firstObjectY = $mFirstObjectY"
//                        )

                        val pixelPerSecond = TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP,
                            event.rawY - mLastTouchY,
                            resources.displayMetrics
                        )

                        val value = event.rawY - mLastTouchY

                        binding.imageViewExpanded.translationY = value

                        if (value in 1f..180f) {
                            binding.viewExpandedBackground.alpha = 0.9f - (value / 200)
                        } else if (value <= -1f && value >= -180f) {
                            binding.viewExpandedBackground.alpha = 0.9f - (value / -200)
                        } else if (value > 180f || value < -180f) {
                            binding.viewExpandedBackground.alpha = 0.0f
                        }

                    }
                    MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                        pointerID = 1000
//                    val displayMetrics = context?.resources?.displayMetrics
//                    val rangeOfRevertImage = displayMetrics?.heightPixels?.div(3.5)?.toInt() ?: 200
                        if (event.rawY - mLastTouchY > 400 || event.rawY - mLastTouchY < -400) {
                            moveFromExpandedToThumbImage()
                        } else {
                            binding.imageViewExpanded.animate()
                                .scaleX(1f)
                                .scaleY(1f)
                                .setDuration(shortAnimationDuration.toLong())
                                .start()
                            binding.imageViewExpanded.animate()
                                .translationY(0f)
                                .setDuration(shortAnimationDuration.toLong())
                                .start()
                            binding.viewExpandedBackground.animate()
                                .alpha(0.9f)
                                .setDuration(shortAnimationDuration.toLong())
                                .start()
                        }
                    }
                    MotionEvent.ACTION_POINTER_UP -> {
                        event.getPointerId(event.actionIndex).takeIf { it == pointerID }?.run {
                            val newPointerIndex = if (event.actionIndex == 0) 1 else 0
                            mLastTouchY = event.getY(newPointerIndex)
                            pointerID = event.getPointerId(newPointerIndex)
                        }
                    }
                }
            } else {
                var dx = 0f
                when (event.actionMasked) {
                    MotionEvent.ACTION_DOWN -> {
                        mLastTouchX = event.rawX - binding.imageViewExpanded.translationX

                        mFirstObjectX = binding.imageViewExpanded.x
                        pointerID = event.getPointerId(0)
                    }
                    MotionEvent.ACTION_MOVE -> {

                        binding.imageViewExpanded.apply {
                            translationX = event.rawX - mLastTouchX
                            Log.i(
                                "ScaleAndZoom",
                                "${left}"
                            )
                        }
                    }
                    MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                        pointerID = 1000
                    }
                    MotionEvent.ACTION_POINTER_UP -> {
                        event.getPointerId(event.actionIndex).takeIf { it == pointerID }?.run {
                            val newPointerIndex = if (event.actionIndex == 0) 1 else 0
                            mLastTouchX = event.getX(newPointerIndex)
                            pointerID = event.getPointerId(newPointerIndex)
                        }
                    }
                }
            }
            imageExpandedGestureDetectorCompat?.onTouchEvent(event)
            imageExpandedScaleGestureDetector?.onTouchEvent(event)
            true
        }
    }

    private fun imageExpandedGestureDetectors() {
        var isScaled = false
        imageExpandedGestureDetectorCompat = GestureDetectorCompat(
            requireContext(),
            object : GestureDetector.SimpleOnGestureListener() {

                override fun onDoubleTap(e: MotionEvent?): Boolean {
                    isScaled = !isScaled
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        binding.imageViewExpanded.resetPivot()
                    }
                    if (isScaled) {
                        binding.imageViewExpanded.animate()
                            .scaleX(2f)
                            .scaleY(2f)
                            .setDuration(shortAnimationDuration.toLong())
                            .start()
                        isEnabledToGesturing = false
                    } else {
                        binding.imageViewExpanded.apply {
                            animate()
                                .scaleX(1f)
                                .scaleY(1f)
                                .translationX(0f)
                                .setDuration(shortAnimationDuration.toLong())
                                .start()
                        }
                        isEnabledToGesturing = true
                    }
                    return true
                }
            }
        )

        var scaleFactor = 1f
        imageExpandedScaleGestureDetector = ScaleGestureDetector(
            requireContext(),
            object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
                override fun onScale(detector: ScaleGestureDetector?): Boolean {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        binding.imageViewExpanded.resetPivot()
                    }
                    scaleFactor *= detector?.scaleFactor ?: 0f
                    binding.imageViewExpanded.apply {
                        scaleX *= scaleFactor
                        scaleY *= scaleFactor
                    }

                    return true
                }
            }
        )
    }

//    private fun hasReachedEdgeX(v: View, newTransX: Float): Boolean {
//        var reachedEdge = true
//        val adjustedWidth = ((v.width * v.scaleX - v.width) / 2f).toInt()
//        val viewLeft = v.left - adjustedWidth
//        val viewRight = v.right + adjustedWidth
//        val p = v.parent as View
//        val pLayoutParams = p.layoutParams as MarginLayoutParams
//        val pLeft = p.left - pLayoutParams.leftMargin
//        val pRight = p.right - pLayoutParams.leftMargin - pLayoutParams.rightMargin
//        val newViewRight = viewRight + newTransX
//        val newViewLeft = viewLeft + newTransX
//        //checks if the view has reached the boundaries of its parent
//        if (newViewLeft > pLeft && newViewRight < pRight) {
//            reachedEdge = false
//        }
//        return reachedEdge
//    }

    private fun moveFromExpandedToThumbImage() {
        binding.imageViewExpanded.apply expandedImage@{
            // Animate back to their original bounds
            currentAnimator = AnimatorSet().apply {
                playTogether(
                    ObjectAnimator.ofFloat(
                        this@expandedImage,
                        View.X,
                        startBounds.left
                    ),
                    ObjectAnimator.ofFloat(
                        this@expandedImage,
                        View.Y,
                        startBounds.top
                    ),
                    ObjectAnimator.ofFloat(
                        this@expandedImage,
                        View.SCALE_X,
                        startScale
                    ),
                    ObjectAnimator.ofFloat(
                        this@expandedImage,
                        View.SCALE_Y,
                        startScale
                    ),
                    ObjectAnimator.ofFloat(
                        binding.viewExpandedBackground,
                        View.ALPHA,
                        0f
                    )
                )
                duration = shortAnimationDuration.toLong() + 100.toLong()
                interpolator = DecelerateInterpolator()
                doOnEnd {
                    binding.imageViewThumb.alpha = 1f
                    visibility = View.GONE
                    binding.viewExpandedBackground.visibility = View.GONE
                    currentAnimator = null
                }
                doOnCancel {
                    binding.imageViewThumb.alpha = 1f
                    visibility = View.GONE
                    binding.viewExpandedBackground.visibility = View.GONE
                    currentAnimator = null
                }
                start()
            }
        }
    }
}