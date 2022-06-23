package com.example.advanceanimation.resultsAll.swipeDrawer

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.*
import androidx.fragment.app.Fragment
import android.view.animation.LinearInterpolator
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.customview.widget.ViewDragHelper
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import com.example.advanceanimation.R
import com.example.advanceanimation.databinding.FragmentResultsAllBinding
import com.example.advanceanimation.databinding.FragmentSwipeDrawerLayoutBinding
import kotlin.math.ceil


class SwipeDrawerLayoutFragment : Fragment() {

    private lateinit var binding: FragmentSwipeDrawerLayoutBinding

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSwipeDrawerLayoutBinding.inflate(inflater, container, false)

//        val mDragger = binding.drawerLayoutSwipe.javaClass.getDeclaredField("mLeftDragger")
//        mDragger.isAccessible = true
//        val dragObj: ViewDragHelper = mDragger[binding.drawerLayoutSwipe] as ViewDragHelper
//        val mEdgeSize = dragObj.javaClass.getDeclaredField("mEdgeSize")
//        mEdgeSize.isAccessible = true
//        val edge: Int = mEdgeSize.getInt(dragObj)
//        mEdgeSize.setInt(dragObj, edge * 100)

        var mLastTouchX = 0f
        var activePointerId = 1000
        binding.drawerLayoutSwipe.setOnTouchListener { v, event ->
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    mLastTouchX = event.getX(event.actionIndex)
                    activePointerId = event.getPointerId(0)

                    val pixelPerSecond =
                        TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP,
                            -290f,
                            resources.displayMetrics
                        )
//                    ceil(-1.0 * 300.0).toFloat()
                    binding.viewToOpen.animate()
                        .translationX(pixelPerSecond)
                        .setDuration(200)
                        .start()

                    Log.i(
                        "SwipeDrawable",
                        "TouchDown = ${binding.viewToOpen.translationX - event.rawX}"
                    )

                }
                MotionEvent.ACTION_MOVE -> {
                    val x: Float = event.findPointerIndex(activePointerId).let { pointerIndex ->
                        event.getX(pointerIndex)
                    }

//                    Log.i(
//                        "SwipeDrawable",
//                        "moving X = $x and Last X = $mLastTouchX and Result = ${x - mLastTouchX} === rawX = ${event.rawX}"
//                    )
                    Log.i(
                        "SwipeDrawable",
                        "moving X = ${binding.viewToOpen.x - event.rawX + event.rawX}"
                    )

                    val pixelPerSecond =
                        TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP,
                            if (-300f + (event.rawX - mLastTouchX) >= 0f) 0f else -300f + (event.rawX - mLastTouchX),
                            resources.displayMetrics
                        )

//                    binding.viewToOpen.animate()
//                        .translationX(pixelPerSecond)
//                        .setDuration(360)
//                        .setInterpolator(LinearInterpolator())
//                        .start()
                    binding.viewToOpen.translationX = pixelPerSecond


//                    binding.drawerLayoutSwipe.openDrawer(GravityCompat.START, true)
                    binding.navigationViewSwipe.translationX = 100f
                }
                MotionEvent.ACTION_UP -> {
                    val pixelPerSecond =
                        TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP,
                            -300f,
                            resources.displayMetrics
                        )

                    binding.viewToOpen.animate()
                        .translationX(pixelPerSecond)
                        .setDuration(200)
                        .start()
                }
            }
            true
        }

        return binding.root
    }

}