package com.example.advanceanimation.resultsAll.listAnim.swipe

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.advanceanimation.R
import com.example.advanceanimation.databinding.FragmentReorderListBinding
import com.example.advanceanimation.launchAndRepeatWithViewLifecycle
import com.example.advanceanimation.resultsAll.listAnim.ListAnimViewModel
import com.example.advanceanimation.resultsAll.listAnim.reorder.ReorderListAdapter
import kotlinx.coroutines.flow.collect


class SwipeListFragment : Fragment() {

    private lateinit var binding: FragmentReorderListBinding

    private lateinit var swipeListAdapter: SwipeListAdapter

    private val listAnimViewModel: ListAnimViewModel by viewModels()

    private lateinit var countTextView: NumberTextView

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReorderListBinding.inflate(inflater, container, false)

        bindList()

        countTextView = NumberTextView(requireContext())
        countTextView.setTextSize(60)
        countTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))

        val lp = FrameLayout.LayoutParams(100, 100, GravityCompat.START)
        lp.setMargins(72, 0, 0, 0)
        binding.frameLayoutReorder.addView(countTextView, lp)

        countTextView.setOnTouchListener { v, event -> true }

        var number = 0

        binding.btnIncrease.setOnClickListener {
            number += 1
            countTextView.setNumber(number, true)
        }

        binding.btnIncrease.setOnLongClickListener {
            number -= 1
            countTextView.setNumber(number, true)
            true
        }

        countTextView.setNumber(number, true)

        return binding.root
    }

    private fun bindList() {

        val itemTouchHelper = ItemTouchHelper(touchHelperCallBack)
        itemTouchHelper.attachToRecyclerView(binding.recReorderList)

        swipeListAdapter = SwipeListAdapter()

        binding.recReorderList.apply {
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
            adapter = swipeListAdapter
        }

        listAnimViewModel.refreshListWithDelay(0L)
        launchAndRepeatWithViewLifecycle {
            listAnimViewModel.rockets.collect {
                swipeListAdapter.submitList(it)
            }
        }
    }

    private val touchHelperCallBack =
        object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                listAnimViewModel.remove(at = viewHolder.bindingAdapterPosition)
            }

        }
}