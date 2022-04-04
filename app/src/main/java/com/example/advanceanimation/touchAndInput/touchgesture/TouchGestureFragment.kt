package com.example.advanceanimation.touchAndInput.touchgesture

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.core.view.GestureDetectorCompat
import androidx.core.view.MotionEventCompat
import androidx.navigation.fragment.findNavController
import com.example.advanceanimation.R
import com.example.advanceanimation.databinding.FragmentSpringPhysicsAnimationBinding
import com.example.advanceanimation.databinding.FragmentTouchGestureBinding


class TouchGestureFragment : Fragment() {

    private lateinit var binding: FragmentTouchGestureBinding

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTouchGestureBinding.inflate(inflater, container, false)

        onClick()

        captureTouchEvent()

        gestureDetector()

        touchEventWithHistoricalCoordinates()

        velocityTracker()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            capturingPointer()

        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun captureTouchEvent() {
        var dx = 0f
        var dy = 0f
        binding.view1.setOnTouchListener { v, event ->
            val action: Int = event.actionMasked
            return@setOnTouchListener when (action) {
                MotionEvent.ACTION_DOWN -> {
                    Log.d("DEBUG_TAG", "Action was DOWN ${event.getPointerId(0)}")
                    dx = v.x - event.rawX
                    dy = v.y - event.rawY
                    true
                }
                MotionEvent.ACTION_MOVE -> {
                    Log.d("DEBUG_TAG", "Action was MOVE")
                    binding.view1.animate()
                        .x(event.rawX + dx)
                        .y(event.rawY + dy)
                        .setDuration(0)
                        .start()
                    true
                }
                MotionEvent.ACTION_UP -> {
                    Log.d("DEBUG_TAG", "Action was UP")
                    binding.view1.animate()
                        .x(40f)
                        .y(350f)
                        .setDuration(300)
                        .start()
                    true
                }
                MotionEvent.ACTION_CANCEL -> {
                    Log.d("DEBUG_TAG", "Action was CANCEL")
                    true
                }
                MotionEvent.ACTION_OUTSIDE -> {
                    Log.d("DEBUG_TAG", "Movement occurred outside bounds of current screen element")
                    true
                }
                else -> false
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun gestureDetector() {
        // Instead of GestureDetector.OnGestureListener we can use GestureDetector.SimpleOnGestureListener() to override whatever method we
        // want. This implements all methods in the GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener, and
        // GestureDetector.OnContextClickListener but return false for unimplemented methods and does nothing
        val mDetector = GestureDetectorCompat(
            requireContext(),
            object : GestureDetector.SimpleOnGestureListener() {
                // That's ACTION_DOWN if first occurs when a tap happened (always make sure to override it and set it to true)
                override fun onDown(e: MotionEvent?): Boolean {
                    Log.d("DEBUG_TAG", "onDown: $e")
                    return true
                }

                // The user performed a down and not performed a move or up yet (Hold the finger on view)
                override fun onShowPress(e: MotionEvent?) {
                    Log.d("DEBUG_TAG", "onShowPress: $e")
                }

                // Notified when a tap occurs with the UP MotionEvent that triggered it
                override fun onSingleTapUp(e: MotionEvent?): Boolean {
                    Log.d("DEBUG_TAG", "onSingleTapUp: $e")
                    return true
                }

                // Notified when a scroll occurs with the initial on down and the current move MotionEvent
                override fun onScroll(
                    e1: MotionEvent?,
                    e2: MotionEvent?,
                    distanceX: Float,
                    distanceY: Float
                ): Boolean {
                    Log.d("DEBUG_TAG", "onScroll: $e1 $e2")
                    return true
                }

                // Notified when a long press occurs with the initial on down MotionEvent that triggered it
                override fun onLongPress(e: MotionEvent?) {
                    Log.d("DEBUG_TAG", "onLongPress: $e")
                }

                // Notified of a fling event when it occurs with the initial on down and matching up MotionEvent
                override fun onFling(
                    e1: MotionEvent?,
                    e2: MotionEvent?,
                    velocityX: Float,
                    velocityY: Float
                ): Boolean {
                    Log.d("DEBUG_TAG", "onFling: $e1 $e2")
                    return true
                }

                // Notified when a double tap occurs
                override fun onDoubleTap(e: MotionEvent?): Boolean {
                    Log.d("DEBUG_TAG", "onDoubleTap: $e")
                    return true
                }

                // Notified when an event within a double-tap gesture occurs, including the down, move and up event
                override fun onDoubleTapEvent(e: MotionEvent?): Boolean {
                    Log.d("DEBUG_TAG", "onDoubleTapEvent: $e")
                    return true
                }

                // Notified when a single-tap occurs
                override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
                    Log.d("DEBUG_TAG", "onSingleTapConfirmed: $e")
                    return true
                }

                // Notified when a context click occurs
                override fun onContextClick(e: MotionEvent?): Boolean {
                    Log.d("DEBUG_TAG", "onContextClick: $e")
                    return true
                }
            })

        binding.view3.setOnTouchListener { v, event ->
            mDetector.onTouchEvent(event)
            true
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun touchEventWithHistoricalCoordinates() {
        binding.view2.setOnTouchListener { v, event ->
            printSamples(event)
            true
        }
    }

    private fun printSamples(motionEvent: MotionEvent) {
        val historySize = motionEvent.historySize
        val pointerCount = motionEvent.pointerCount
        for (h in 0 until historySize) {
            Log.i("DEBUG_TAG", "At time %d: ${motionEvent.getHistoricalEventTime(h)}")
            for (p in 0 until pointerCount) {
                Log.i(
                    "DEBUG_TAG",
                    "pointer %d: (%f, %f): ${motionEvent.getPointerId(p)}, ${
                        motionEvent.getHistoricalX(
                            p,
                            h
                        )
                    }, ${motionEvent.getHistoricalY(p, h)}"
                )
            }
            Log.i("DEBUG_TAG", "At time %d: ${motionEvent.eventTime}")
            for (p in 0 until pointerCount) {
                Log.i(
                    "DEBUG_TAG",
                    "pointer %d: (%f, %f): ${motionEvent.getPointerId(p)}, ${motionEvent.getX(p)}, ${
                        motionEvent.getY(
                            p
                        )
                    }"
                )
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility", "Recycle")
    private fun velocityTracker() {
        var mVelocityTracker: VelocityTracker? = null
        binding.view4.setOnTouchListener { v, event ->
            /* getActionMasked() = Return the masked action being performed (such as ACTION_DOWN and ...), without pointer index information.
            Use the getActionIndex() to return the index associated with pointer action
             */
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    // Reset the velocity tracker back to its initial state
                    mVelocityTracker?.clear()
                    // Retrieve a new VelocityTracker object to watch the velocity of the motion
                    mVelocityTracker = mVelocityTracker ?: VelocityTracker.obtain()
                    // Add the user's movement to tracker
                    mVelocityTracker?.addMovement(event)
                }
                MotionEvent.ACTION_MOVE -> {
                    mVelocityTracker?.apply {
                        val pointerId: Int = event.getPointerId(event.actionIndex)
                        addMovement(event)
                        /*
                        When we wanna determine the velocity, call the computeCurrentVelocity(). Then call getXVelocity()
                        and getYVelocity() to retrieve the velocity for each pointer ID.
                         */
                        computeCurrentVelocity(1000)
                        // Log velocity of pixels per second
                        Log.d("DEBUG_TAG", "X velocity: ${getXVelocity(pointerId)}")
                        Log.d("DEBUG_TAG", "Y velocity: ${getYVelocity(pointerId)}")
                    }
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    // Return a VelocityTracker back to be re-used by other
                    mVelocityTracker?.recycle()
                    mVelocityTracker = null
                }
            }
            true
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun capturingPointer() {
        binding.view5.setOnFocusChangeListener { v, hasFocus ->
            // request pointer capture
            binding.view5.requestPointerCapture()
            // releae pointer capture
//            binding.view5.releasePointerCapture()
        }

        // The following code example shows how to use OnCapturedPointerListener
        binding.view5.setOnCapturedPointerListener { view, event ->
            // Get the coordinate required by our app
            val verticalOffset: Float = event.y
            Log.d("DEBUG_TAG", "capturingPointer: $verticalOffset")
            // Use the coordinate to update our view
            // Return true if the event was successfully provessed
            true
        }
    }

    private fun onClick() {
        binding.imageBtnBack.setOnClickListener { findNavController().popBackStack() }
    }

}