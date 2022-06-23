package com.example.advanceanimation.touchAndInput.multiTouchGesture

import android.annotation.SuppressLint
import android.graphics.PointF
import android.graphics.RectF
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.MotionEventCompat
import androidx.navigation.fragment.findNavController
import com.example.advanceanimation.R
import com.example.advanceanimation.databinding.FragmentMultiTouchGestureBinding
import com.example.advanceanimation.databinding.FragmentScrollGestureBinding
import kotlin.math.max
import kotlin.math.min


class MultiTouchGestureFragment : Fragment() {

    private lateinit var binding: FragmentMultiTouchGestureBinding

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMultiTouchGestureBinding.inflate(inflater, container, false)

        onClick()

        trackMultiplePointers()

        draggingObject()

        touchToScale()

        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun trackMultiplePointers() {
        var mActionPointerId = 0
        binding.view1.setOnTouchListener { v, event ->
            val (xPos: Int, yPos: Int) = event.actionMasked.let { action ->
                Log.d("DEBUG_TAG", "The action is ${actionToString(action)}")
                // Get the index of the pointer associated with the action
                event.getX(event.actionIndex).toInt() to event.getY(event.actionIndex).toInt()
            }

            Log.d("DEBUG_TAG", "The xPos => $xPos and yPos => $yPos")

            if (event.pointerCount > 1) {
                Log.d("DEBUG_TAG", "Multitouch event")
            } else {
                Log.d("DEBUG_TAG", "Single touch event")
            }

            // Get the pointer ID
            mActionPointerId = event.getPointerId(0)

            // Use the pointer ID to find the index of active pointer and fetch its position
            val (x: Float, y: Float) = event.findPointerIndex(mActionPointerId)
                .let { pointerIndex ->
                    // Get the pointer's current position
                    // `to` create a tuple of type from this and that
                    event.getX(pointerIndex) to event.getY(pointerIndex)
                }



            true
        }
    }


    @SuppressLint("ClickableViewAccessibility")
    private fun draggingObject() {
        var mActivePointerId = 100 // Just one invalid pointer ID
        var mLastTouchX = 0f
        var mLastTouchY = 0f
        binding.view2.setOnTouchListener { v, event ->
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    mLastTouchX = event.getX(event.actionIndex)
                    mLastTouchY = event.getY(event.actionIndex)

                    mActivePointerId = event.getPointerId(0)
                }

                MotionEvent.ACTION_MOVE -> {
                    // Find the index of the active pointer and fetch its position
                    val (x: Float, y: Float) = event.findPointerIndex(mActivePointerId).let { pointerIndex ->
                        event.getX(pointerIndex) to event.getY(pointerIndex)
                    }

//                    binding.view2.animate()
//                        .x(binding.view2.x + x - mLastTouchX)
//                        .y(binding.view2.y + y - mLastTouchY)
//                        .setDuration(0)
//                        .start()
                    binding.view2.x = binding.view2.x + x - mLastTouchX
                    binding.view2.y = binding.view2.y + y - mLastTouchY
//                    binding.view2.invalidate()

                    // Remember this touch position for the next move event
                    mLastTouchX = x
                    mLastTouchY = y
                }

                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> mActivePointerId = 100

                MotionEvent.ACTION_POINTER_UP -> {
                    event.getPointerId(event.actionIndex).takeIf { it == mActivePointerId }?.run {
                        // This was our active pointer going up. Choose a new active pointer and adjust accordingly
                        val newPointerIndex = if (event.actionIndex == 0) 1 else 0
                        mLastTouchX = event.getX(newPointerIndex)
                        mLastTouchY = event.getY(newPointerIndex)
                        mActivePointerId = event.getPointerId(newPointerIndex)
                    }
                }
            }
            true
        }
    }


    @SuppressLint("ClickableViewAccessibility")
    private fun touchToScale() {
        var mScaleFactor = 1f
        val scaleListener = object : ScaleGestureDetector.SimpleOnScaleGestureListener() {

            // Responds to scaling events for a gesture in progress
            override fun onScale(detector: ScaleGestureDetector): Boolean {
                // scaleFactor = returns the scaling factor from the previous scale event to the current event
                mScaleFactor *= detector.scaleFactor
                Log.d("DEBUG_TAG", "onScale: ${detector.scaleFactor}")

                // max will return greater of two value we give and min will give us smaller of two value we gave to
//                mScaleFactor = max(0.1f, min(mScaleFactor, 5.0f))

                binding.view3.apply {
                    scaleX *= mScaleFactor
                    scaleY *= mScaleFactor
                }

                binding.view3.invalidate()
                return true
            }

            // Responds to beginning of a scaling gesture
            override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
                return super.onScaleBegin(detector)
            }

            // Responds to end of a scale gesture
            override fun onScaleEnd(detector: ScaleGestureDetector) {
                super.onScaleEnd(detector)
            }
        }
        val mScaleDetector = ScaleGestureDetector(context, scaleListener)

        // set the scale detector to touch event
        binding.view3.setOnTouchListener { v, event ->
            mScaleDetector.onTouchEvent(event)
            true
        }
    }

    // Given an action int returns a string description
    private fun actionToString(action: Int) : String {
        return when (action) {
            MotionEvent.ACTION_DOWN -> "Down"
            MotionEvent.ACTION_MOVE -> "Move"
            MotionEvent.ACTION_POINTER_DOWN -> "Pointer Down"
            MotionEvent.ACTION_UP -> "Up"
            MotionEvent.ACTION_POINTER_UP -> "Pointer UP"
            MotionEvent.ACTION_OUTSIDE -> "Outside"
            MotionEvent.ACTION_CANCEL -> "Cancel"
            else -> ""
        }
    }

    private fun onClick() {
        binding.imageBtnBack.setOnClickListener { findNavController().popBackStack() }
    }

}