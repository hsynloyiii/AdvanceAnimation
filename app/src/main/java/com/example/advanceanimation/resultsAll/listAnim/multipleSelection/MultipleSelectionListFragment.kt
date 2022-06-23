package com.example.advanceanimation.resultsAll.listAnim.multipleSelection

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.advanceanimation.R
import com.example.advanceanimation.databinding.FragmentMultipleSelectionListBinding
import com.example.advanceanimation.databinding.FragmentReorderListBinding
import com.example.advanceanimation.launchAndRepeatWithViewLifecycle
import com.example.advanceanimation.resultsAll.listAnim.ListAnimViewModel
import com.example.advanceanimation.resultsAll.listAnim.reorder.ReorderListAdapter
import kotlinx.coroutines.flow.collect


class MultipleSelectionListFragment : Fragment() {

    private lateinit var binding: FragmentMultipleSelectionListBinding

    private lateinit var multipleSelectionAdapter: MultipleSelectionListAdapter

    private val listAnimViewModel: ListAnimViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMultipleSelectionListBinding.inflate(inflater, container, false)

        bindList()

        return binding.root
    }

    private fun bindList() {
        multipleSelectionAdapter = MultipleSelectionListAdapter()

        /*
        Here we create the tracker object
        selectionId = a string to identify our selection in the context of the activity or fragment
        recyclerView = the rec where we apply the tracker
        keyProvider = the source of selection key
        detailsLookup = the source of information about recyclerView items
        storage = strategy for type-safe storage of the selection state (Strategy for storing keys in saved state)

        The most important is SelectionPredicates =
            We passed SelectionPredicates.createSelectAnything() which allows us to select multiple items in our list

        SelectionTracker.SelectionPredicate = provide a restrict to which item can be selected or limit the number of items that
        can be selected as well as allowing the selection code to be placed into "Single select" mode and ...

        If we want an id if selected item we need observer which we added in adapter
         */

        binding.recMultipleSelection.apply {
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
            adapter = multipleSelectionAdapter
        }

        val tracker = SelectionTracker.Builder(
            "my-selection-id",
            binding.recMultipleSelection,
            MyItemKeyProvider(multipleSelectionAdapter),
            MyItemDetailsLookup(binding.recMultipleSelection),
            StorageStrategy.createLongStorage()
        ).withSelectionPredicate(
            SelectionPredicates.createSelectAnything()
        ).build()

        multipleSelectionAdapter.tracker = tracker

        listAnimViewModel.refreshListWithDelay(0L)
        launchAndRepeatWithViewLifecycle {
            listAnimViewModel.rockets.collect {
                multipleSelectionAdapter.submitList(it)
                Log.i("MultipleSelectionTagToDebug", "bindList: $it")
            }
        }

    }

}