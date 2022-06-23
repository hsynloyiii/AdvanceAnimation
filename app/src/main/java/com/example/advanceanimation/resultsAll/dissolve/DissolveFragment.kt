package com.example.advanceanimation.resultsAll.dissolve

import android.animation.TimeInterpolator
import android.annotation.SuppressLint
import android.os.Bundle
import android.transition.TransitionManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.animation.PathInterpolatorCompat
import androidx.fragment.app.viewModels
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
import androidx.lifecycle.*
import com.example.advanceanimation.R
import com.example.advanceanimation.databinding.FragmentDissolveBinding
import com.example.advanceanimation.databinding.FragmentSwipeDrawerLayoutBinding
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class DissolveViewModel : ViewModel() {

    companion object {
        private val IMAGES = listOf(
            R.drawable.rocket_thrust1,
            R.drawable.rocket_thrust2,
            R.drawable.rocket_thrust3
        )
    }

    private val position = MutableStateFlow(0)

    val image = position.map {
        IMAGES[it % IMAGES.size]
    }

    fun nextImage() {
        position.update {
            position.value + 1
        }
    }
}

class DissolveFragment : Fragment() {

    companion object {
        val FAST_OUT_SLOW_IN: TimeInterpolator by lazy(LazyThreadSafetyMode.NONE) {
            PathInterpolatorCompat.create(0.4f, 0f, 0.2f, 1f)
        }
    }

    private lateinit var binding: FragmentDissolveBinding

    private val dissolveVM: DissolveViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDissolveBinding.inflate(inflater, container, false)

        val dissolve = Dissolve().apply {
            addTarget(binding.imageViewDissolve)
            duration = 200L
            interpolator = FastOutLinearInInterpolator()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                dissolveVM.image.collect { resId ->
                    TransitionManager.beginDelayedTransition(binding.cardViewDissolve, dissolve)
                    // Here, we are simply changing the image shown on the image view
                    binding.imageViewDissolve.setImageResource(resId)
                }
            }
        }

        binding.cardViewDissolve.setOnClickListener {
            dissolveVM.nextImage()
        }

        return binding.root
    }

}