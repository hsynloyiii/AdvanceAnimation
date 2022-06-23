package com.example.advanceanimation.resultsAll.swipeFragmentContainer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Fade
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.advanceanimation.R
import com.example.advanceanimation.databinding.ActivityMainSwipeContainerBinding

class MainSwipeContainerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainSwipeContainerBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_swipe_container)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostSwipeContainer) as NavHostFragment
        val navController = navHostFragment.navController

        val fragmentList = listOf(
            FirstSwipeFragment(), SecondSwipeFragment()
        )

        val swipeContainerAdapter = SwipeContainerAdapter(
            fragmentList, supportFragmentManager, lifecycle
        )

        binding.viewPagerSwipeContainer.apply {
            adapter = swipeContainerAdapter
//            isUserInputEnabled = if (fragmentList.is)
            setPageTransformer(DepthPageTransformer())
            registerOnPageChangeCallback(
                object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        when (position) {
                            0 -> {
                                isUserInputEnabled = false
//                                navController.navigate(R.id.firstSwipeFragment)
//                                println(navController.currentDestination)
                                fragmentList[1].onStop()
                            }
                            1 -> {
                                isUserInputEnabled = true
//                                navController.navigate(R.id.secondSwipeFragment)
//                                println(navController.currentDestination)
                            }
                        }
                    }

                    override fun onPageScrollStateChanged(state: Int) {
                        when (state) {
                            ViewPager2.SCROLL_STATE_DRAGGING -> println("Dragging => $currentItem")
                            ViewPager2.SCROLL_STATE_IDLE -> println("Idle => $currentItem")
                            ViewPager2.SCROLL_STATE_SETTLING -> println("Settling => $currentItem")
                        }
                    }

//                    override fun onPageScrolled(
//                        position: Int,
//                        positionOffset: Float,
//                        positionOffsetPixels: Int
//                    ) {
//                        println("The position when scrolled => $position, Position offset => $positionOffset, Position offset pixel => $positionOffsetPixels")
//                    }
                }
            )
        }

    }
}

class SwipeContainerAdapter(
    private val fragmentList: List<Fragment>,
    fm: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fm, lifecycle) {

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment = fragmentList[position]


}