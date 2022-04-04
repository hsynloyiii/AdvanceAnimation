package com.example.advanceanimation.touchAndInput.touchEventInViewGroup

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.advanceanimation.R
import com.example.advanceanimation.databinding.FragmentMultiTouchGestureBinding
import com.example.advanceanimation.databinding.FragmentTouchEventInViewGroupBinding


class TouchEventInViewGroupFragment : Fragment() {

    private lateinit var binding: FragmentTouchEventInViewGroupBinding

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTouchEventInViewGroupBinding.inflate(inflater, container, false)

        onClick()

        extendChildViewTouchableAre()

        return binding.root
    }

    private fun extendChildViewTouchableAre() {
        // Post in the parent's message queue to make sure the parent lays out its children before you call getHitRect()
        binding.linearViewGroup.post {
            // The bounds for delegate view (an ImageButton)
            val delegatedArea = Rect()
            val myButton = binding.imageBtnBack.apply {
                isEnabled = true
                setOnClickListener {
                    Toast.makeText(
                        context,
                        "Touch occurred within ImagerButton touch region",
                        Toast.LENGTH_SHORT
                    ).show()
                    findNavController().popBackStack()
                }

                // The hit rectangle for ImageButton
                getHitRect(delegatedArea)
            }

            // Extend the touch area of the ImageButton beyond its bounds on the right and bottom
            delegatedArea.right += 500
            delegatedArea.bottom += 100

            // Set the TouchDelegate on the parent view, such that touches within the touch delegate bounds are routed to the child
            (myButton.parent as? View)?.apply {
                /* Instantiate a TouchDelegate.
                "delegateArea" is the bounds in local coordinates of the containing view to be mapped to the delegate view.
                "myButton" is the child view that should receive motion events
                 */
                touchDelegate = TouchDelegate(delegatedArea, myButton)
            }
        }
    }

    private fun onClick() {
//        binding.imageBtnBack.setOnClickListener { findNavController().popBackStack() }
    }
}

class MyViewGroup @JvmOverloads constructor(
    context: Context,
    private val mTouchSlop: Int = ViewConfiguration.get(context).scaledTouchSlop
) : ViewGroup(context) {
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
    }

    var mIsScrolling: Boolean? = null

    // This just determines whether we want to intercept the motion (if we return true and onTouchEvent will be called and do scrolling)
    // or not (if we return false)
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return when (ev.actionMasked) {
            // always handle the case of touch event being complete
            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                // Release the scroll
                mIsScrolling = false
                false // Do not intercept touch event, let the child handle it
            }
            MotionEvent.ACTION_MOVE -> {
                if (mIsScrolling != false) {
                    // We're currently scrolling so intercept the touch event
                    true
                } else {
                    // If the user has dragged their finger horizontally more than the touch slop, start the scroll
                    // left as an exercise for the reader
//                    val xDiff: Int = calculateDistanceX(ev)

                    // Touch slop should be calculated using ViewConfiguration
                    // constants.
//                    if (xDiff > mTouchSlop) {
//                        // Start scrolling!
//                        mIsScrolling = true
//                        true
//                    } else {
//                        false
//                    }
                    true
                }
            }
            else -> {
                // In general we don't wanna intercept the touch events. They should be handled by the child view
                false
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        // Here we actually handle the touch event (e.g if the action is ACTION_MOVE, scroll this container)
        // This method will only called if the touch event was intercepted in onInterceptTouchEvent()
        return super.onTouchEvent(event)
    }
}