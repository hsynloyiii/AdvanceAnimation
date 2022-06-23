package com.example.advanceanimation.resultsAll.swipeFragmentContainer

import android.view.View
import androidx.annotation.RequiresApi
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

private const val MIN_SCALE = 0.99f

@RequiresApi(21)
class DepthPageTransformer: ViewPager2.PageTransformer {
    override fun transformPage(page: View, position: Float) {
        page.apply {
            val pageWidth = width
            when {
                position < -1 -> {
                    alpha = 0f
                }
                position <= 0 -> {
                    pivotX = measuredHeight.toFloat()
                    pivotY = measuredHeight.toFloat()
                    alpha = 1f
                    translationX = pageWidth * -position
                    translationZ = 0f
                    val scaleFactor = (MIN_SCALE + (1 - MIN_SCALE) * (1 - abs(position)))
                    scaleX = scaleFactor
                    scaleY = scaleFactor
                }
                position <= 1 -> {
                    alpha = 1 - position + 0.92f
                    translationX = 0f
                    translationZ = 1f
                    scaleX = 1f
                    scaleY = 1f
                }
                else -> {
                    alpha = 0f
                }
            }
        }
    }
}