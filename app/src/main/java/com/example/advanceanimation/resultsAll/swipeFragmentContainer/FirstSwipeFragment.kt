package com.example.advanceanimation.resultsAll.swipeFragmentContainer

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.advanceanimation.R
import com.example.advanceanimation.databinding.FragmentFirstSwipeBinding
import com.example.advanceanimation.databinding.FragmentSecondSwipeBinding


class FirstSwipeFragment : Fragment() {

    private lateinit var binding: FragmentFirstSwipeBinding

    @SuppressLint("CutPasteId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFirstSwipeBinding.inflate(inflater, container, false)

//        (activity as MainSwipeContainerActivity)
//            .findViewById<ViewPager2>(R.id.viewPagerSwipeContainer).isUserInputEnabled = false

        println("I'm first fragment swipe")

        binding.btnNextSwipeFirstFragment.setOnClickListener {
            (activity as MainSwipeContainerActivity)
                .findViewById<ViewPager2>(R.id.viewPagerSwipeContainer).currentItem = 1
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        println("I'm first fragment swipe OnResume")
    }

    override fun onStart() {
        super.onStart()
        println("I'm first fragment swipe OnStart")
    }
}
