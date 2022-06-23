package com.example.advanceanimation.resultsAll.listAnim.reorder

import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.advanceanimation.R
import com.example.advanceanimation.databinding.FragmentReorderListBinding
import com.example.advanceanimation.databinding.FragmentStaggerRecAnimBinding
import com.example.advanceanimation.launchAndRepeatWithViewLifecycle
import com.example.advanceanimation.resultsAll.listAnim.ListAnimViewModel
import com.example.advanceanimation.resultsAll.listAnim.stagger.StaggerRecAdapter
import kotlinx.coroutines.flow.collect


class ReorderListFragment : Fragment() {

    private lateinit var binding: FragmentReorderListBinding

    private lateinit var reorderListAdapter: ReorderListAdapter

    private val listAnimViewModel: ListAnimViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReorderListBinding.inflate(inflater, container, false)

        bindList()

        return binding.root
    }

    private fun bindList() {

        val itemTouchHelper = ItemTouchHelper(touchHelperCallBack)
        itemTouchHelper.attachToRecyclerView(binding.recReorderList)

        reorderListAdapter = ReorderListAdapter { holder ->
            itemTouchHelper.startDrag(holder)
        }

        binding.recReorderList.apply {
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
            adapter = reorderListAdapter
        }

        listAnimViewModel.refreshListWithDelay(0L)
        launchAndRepeatWithViewLifecycle {
            listAnimViewModel.rockets.collect {
                reorderListAdapter.submitList(it)
            }
        }
    }

    // Our touch helper for recycler view
    private val touchHelperCallBack = object : ItemTouchHelper.Callback() {

        // Should return a composite flag which defines the enabled move directions in each state
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int {
            return makeMovementFlags(
                ItemTouchHelper.UP or ItemTouchHelper.DOWN or
                        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT,
                0 // We don't enable the swipe gesture
            )
        }

        /* Called when ItemTouchHelper wants to move the dragged item from its old position to the new position
        If this method returns true, ItemTouchHelper assumes viewHolder has been moved to the adapter position of target ViewHolder
        Params:
            recyclerView – The RecyclerView to which ItemTouchHelper is attached to.
            viewHolder – The ViewHolder which is being dragged by the user.
            target – The ViewHolder over which the currently active item is being dragged.
        Return:
            True if the viewHolder has been moved to the adapter position of target.
         */
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            listAnimViewModel.move(viewHolder.bindingAdapterPosition, target.bindingAdapterPosition)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            // Nothing
        }


        /*
        Called when the ViewHolder swiped or dragged by the ItemTouchHelper is changed.
         */
        override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
            super.onSelectedChanged(viewHolder, actionState)
            val view = viewHolder?.itemView ?: return
            val sizeInPixel =
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8f, resources.displayMetrics)
            when (actionState) {
                ItemTouchHelper.ACTION_STATE_DRAG -> {
                    view.animate().setDuration(150L).translationZ(sizeInPixel)
                }
            }
        }


        /*
        Called by the ItemTouchHelper when the user interaction with an element is over and it also completed its animation.
        ViewHolder in params is the view that interacted by user
         */
        override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
            super.clearView(recyclerView, viewHolder)
            viewHolder.itemView.animate().setDuration(150L).translationZ(0f)
        }

        override fun isLongPressDragEnabled(): Boolean {
            // We handle the long-press in our side for better touch feedback
            return false
        }

        override fun isItemViewSwipeEnabled(): Boolean {
            return false
        }
    }
}