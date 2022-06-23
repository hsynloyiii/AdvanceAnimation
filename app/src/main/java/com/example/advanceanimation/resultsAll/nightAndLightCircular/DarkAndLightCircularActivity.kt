package com.example.advanceanimation.resultsAll.nightAndLightCircular

import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import androidx.core.animation.doOnEnd
import androidx.core.view.LayoutInflaterCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.advanceanimation.R
import com.example.advanceanimation.databinding.ActivityDarkAndLightCircularBinding
import kotlin.math.hypot

class DarkAndLightCircularActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDarkAndLightCircularBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        // For Dark/Light theme changing
        LayoutInflaterCompat.setFactory2(
            LayoutInflater.from(this),
            MyLayoutInflater(delegate)
        )

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dark_and_light_circular)
        setContentView(binding.root)

        ThemeManager.theme = ThemeManager.Theme.LIGHT

        binding.imageView.visibility = View.GONE
        binding.button.setOnClickListener {
            setTheme(
                if (ThemeManager.theme == ThemeManager.Theme.DARK) ThemeManager.Theme.LIGHT else ThemeManager.Theme.DARK,
                toDark = ThemeManager.theme != ThemeManager.Theme.DARK
            )
        }

    }

    private fun setTheme(theme: ThemeManager.Theme, toDark: Boolean) =
        lifecycleScope.launchWhenStarted {

//        if (!animate) {
//            ThemeManager.theme = theme
//            return
//        }

            val imageView = binding.imageView
            val container = binding.container
            if (imageView.isVisible)
                return@launchWhenStarted

            try {
                val w = container.measuredWidth
                val h = container.measuredHeight

                val bitmap =
                    Bitmap.createBitmap(
                        w,
                        h,
                        Bitmap.Config.ARGB_8888
                    ) // draw all content into bitmap
                val canvas = Canvas(bitmap)
                invalidateCachedViews(container)
                container.draw(canvas)

                binding.frameLayout.removeView(binding.imageView)
                if (toDark) {
                    binding.frameLayout.addView(
                        imageView,
                        0,
                        FrameLayout.LayoutParams(
                            FrameLayout.LayoutParams.MATCH_PARENT,
                            FrameLayout.LayoutParams.MATCH_PARENT
                        )
                    )
                } else {
                    binding.frameLayout.addView(
                        imageView,
                        1,
                        FrameLayout.LayoutParams(
                            FrameLayout.LayoutParams.MATCH_PARENT,
                            FrameLayout.LayoutParams.MATCH_PARENT
                        )
                    )
                }
                imageView.setImageBitmap(bitmap)
                imageView.visibility = View.VISIBLE


                val finalRadius = hypot(w.toFloat(), h.toFloat())

                ViewAnimationUtils.createCircularReveal(
                    if (toDark) container else imageView,
                    w / 2, h / 2,
                    if (toDark) 0f else finalRadius,
                    if (toDark) finalRadius else 0f
                )
                    .apply {
                        duration = 400L
                        interpolator = CubicBezierInterpolator(0.455, 0.03, 0.515, 0.955)
                        doOnEnd {
                            imageView.setImageDrawable(null)
                            imageView.visibility = View.GONE
                        }
                        start()
                    }
            } catch (e: Throwable) {
                imageView.setImageDrawable(null)
                binding.frameLayout.removeView(imageView)
            }

            ThemeManager.theme = theme
        }

    private fun invalidateCachedViews(parent: View) {
        /*
        Indicates what type of layer is currently associated with this view. By default a view does not have a layer,
        and the layer type is LAYER_TYPE_NONE.
         */
        val layerType: Int = parent.layerType
        if (layerType != View.LAYER_TYPE_NONE)
            parent.invalidate()
        if (parent is ViewGroup) {
            for (i in 0 until parent.childCount) {
                invalidateCachedViews(parent.getChildAt(i))
            }
        }
    }
}


