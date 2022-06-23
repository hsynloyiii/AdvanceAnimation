package com.example.advanceanimation.resultsAll.fabTransform

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.annotation.DrawableRes
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.advanceanimation.R
import com.example.advanceanimation.databinding.FragmentFabTransformBinding
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FabTransformFragment : Fragment() {

    private lateinit var binding: FragmentFabTransformBinding

    private val fabTransformVM: FabTransformViewModel by viewModels()

    private class RocketItemHolder(val parent: LinearLayout, listener: View.OnClickListener) {
        val image = parent.findViewById(R.id.image) as ImageView
        val title = parent.findViewById(R.id.title) as TextView

        init {
            parent.setOnClickListener(listener)
        }
    }

    @SuppressLint("SetTextI18n")
    private val rocketOnClick = View.OnClickListener {
        val name = it.getTag(R.id.tag_name) as String
        binding.messageFragmentFABTransform.text = "You selected $name"
        binding.fabFragmentFabTransform.isExpanded = false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFabTransformBinding.inflate(inflater, container, false)
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (binding.fabFragmentFabTransform.isExpanded) {
                        binding.fabFragmentFabTransform.isExpanded = false
                    } else {
                        findNavController().popBackStack()
                    }
                }
            }
        )

        val rocketHolders: List<RocketItemHolder> = listOf(
            RocketItemHolder(binding.root.findViewById(R.id.rocket_1), rocketOnClick),
            RocketItemHolder(binding.root.findViewById(R.id.rocket_2), rocketOnClick),
            RocketItemHolder(binding.root.findViewById(R.id.rocket_3), rocketOnClick)
        )

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {

                fabTransformVM.rockets.collect { rockets ->
                    rocketHolders.forEachIndexed { index, rocketItemHolder ->
                        if (rockets.size > index) {
                            val rocket = rockets[index]
                            rocketItemHolder.apply {
                                parent.isVisible = true
                                parent.setTag(R.id.tag_name, rocket.name)
                                title.text = rocket.name
                                Glide.with(image)
                                    .load(rocket.image)
                                    .transform(CircleCrop())
                                    .into(image)
                            }
                        } else {
                            rocketItemHolder.parent.isVisible = false
                        }
                    }
                }

            }
        }

        binding.fabFragmentFabTransform.apply {
            setOnClickListener {
                /*
                Expand the FAB. The CoordinatorLayout transforms the FAB into the view marked with
                FabTransformationSheetBehavior.
                Also the view marked with FabTransformationScrimBehavior is faded in as a content scrim
                 */
                isExpanded = true
            }
        }

        binding.scrimBackgroundFragmentFabTransform.setOnClickListener {
            // Shrink the menu sheet back into the FAB
            binding.fabFragmentFabTransform.isExpanded = false
        }

        return binding.root
    }

}