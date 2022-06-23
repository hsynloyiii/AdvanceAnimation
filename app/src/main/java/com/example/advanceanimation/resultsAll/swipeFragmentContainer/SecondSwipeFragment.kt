package com.example.advanceanimation.resultsAll.swipeFragmentContainer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.advanceanimation.R
import com.example.advanceanimation.databinding.FragmentSecondSwipeBinding


class SecondSwipeFragment : Fragment() {

    private lateinit var binding: FragmentSecondSwipeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSecondSwipeBinding.inflate(inflater, container, false)

        println("I'm Second fragment swipe")


//        if (findNavController().currentDestination?.id == R.id.secondSwipeFragment) {
//            (activity as MainSwipeContainerActivity)
//                .findViewById<ViewPager2>(R.id.viewPagerSwipeContainer).currentItem = 1
//        }

        binding.btnBackSwipeSecondFragment.setOnClickListener {
            (activity as MainSwipeContainerActivity)
                .findViewById<ViewPager2>(R.id.viewPagerSwipeContainer).currentItem = 0
        }


        return binding.root
    }

    override fun onStart() {
        super.onStart()
        println("I'm second fragment swipe OnStart")
    }

    override fun onResume() {
        super.onResume()
        println("I'm second fragment swipe OnResume")

    }
}