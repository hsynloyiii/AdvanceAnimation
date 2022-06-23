package com.example.advanceanimation.resultsAll.listAnim.stagger

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.advanceanimation.databinding.FragmentStaggerRecAnimBinding
import com.example.advanceanimation.launchAndRepeatWithViewLifecycle
import com.example.advanceanimation.resultsAll.listAnim.ListAnimViewModel
import kotlinx.coroutines.flow.collect


class StaggerRecAnimFragment : Fragment() {

    private lateinit var binding: FragmentStaggerRecAnimBinding

    private lateinit var staggerRecAdapter: StaggerRecAdapter

    private val listAnimViewModel: ListAnimViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStaggerRecAnimBinding.inflate(inflater, container, false)

        bindList()

        binding.btnRefreshListFragmentStagger.setOnClickListener {
            listAnimViewModel.run {
                empty()
                refreshListWithDelay(300L)
            }
        }

        return binding.root
    }

    private fun bindList() {
        staggerRecAdapter = StaggerRecAdapter()

        binding.recStaggerItemAnimator.apply {
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
            adapter = staggerRecAdapter

            edgeEffectFactory = staggerRecAdapter.edgeEffectFactory

            overScrollMode = RecyclerView.OVER_SCROLL_IF_CONTENT_SCROLLS

            itemAnimator = object : DefaultItemAnimator() {
                /*
                Called when an item is added to the RecyclerView, in this we can choose whether and
                    how to animate that change

                We must always must always call dispatchAddFinished(RecyclerView.ViewHolder) when done,
                    either immediately (if no animation will occur) or after the animation actually finishes.

                Params - holder = The item that is being added
                 */
                override fun animateAdd(holder: RecyclerView.ViewHolder?): Boolean {
                    dispatchAddFinished(holder) // Method to be called by subclasses when an add animation is done
                    dispatchAddStarting(holder) // Method to be called by subclasses when an add animation is being started
                    return false // We animate item addition in our side, so we disable it in RecyclerView
                }
            }
        }
        listAnimViewModel.refreshListWithDelay(300L)

        launchAndRepeatWithViewLifecycle {
            listAnimViewModel.rockets.collect { rockets ->
                TransitionManager.beginDelayedTransition(
                    binding.recStaggerItemAnimator,
                    StaggerTransition()
                )
                staggerRecAdapter.submitList(rockets)
            }
        }
    }

}