package com.example.advanceanimation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import androidx.core.view.*
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.navigation.navOptions
import com.example.advanceanimation.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home,
            container,
            false
        )

        onClick()

        /*
        setOnApplyWindowInsetsListener = Applying window insets to appropriate view.
        OnApplyWindowInsetsListener = Listener for applying window insets on a view in a custom way.
        It give us back the view and WindowInsetsCompat (to describe a set of insets for window content)

        getInsets(WindowInsetsCompat.Type.systemBars()) = get insets for all system bars. and also this return the Inset
        (An Insets instance holds four integer offsets which describe changes to the four edges of a Rectangle)
        The Type.systemBars() returns : Includes statusBars(), captionBar() as well as navigationBars(), but not ime().
         */
        ViewCompat.setOnApplyWindowInsetsListener(binding.btnGoToResults) { view, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            /*
            Apply the insets as a margin to the view
             */
            view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                topMargin = insets.top
            }

            /*
            Return CONSUMED if we don't want the window insets to keep being passed down to descendant views
             */
            WindowInsetsCompat.CONSUMED
        }

        return binding.root
    }

    private fun onClick() {
        binding.matPropertyAnimation.navigateToDestination(
            HomeFragmentDirections
                .actionHomeFragmentToPropertyAnimFragment()
        )

        binding.matAnimateDrawable.navigateToDestination(
            HomeFragmentDirections
                .actionHomeFragmentToAnimateDrawableFragment()
        )

        binding.matRevealOrHideAnimation.navigateToDestination(
            HomeFragmentDirections
                .actionHomeFragmentToRevealOrHideViewUsingAnimationFragment()
        )

        binding.matMoveViewUsingAnimation.navigateToDestination(
            HomeFragmentDirections
                .actionHomeFragmentToMoveViewUsingAnimationFragment()
        )

        binding.matZoomAnimation.navigateToDestination(
            HomeFragmentDirections
                .actionHomeFragmentToZoomAnimationFragment()
        )
        binding.matSpringAnimation.navigateToDestination(
            HomeFragmentDirections
                .actionHomeFragmentToSpringPhysicsAnimationFragment()
        )
        binding.matTouchGesture.navigateToDestination(
            HomeFragmentDirections
                .actionHomeFragmentToTouchGestureFragment()
        )
        binding.matScrollGesture.navigateToDestination(
            HomeFragmentDirections
                .actionHomeFragmentToScrollGestureFragment()
        )
        binding.matMultiTouchGesture.navigateToDestination(
            HomeFragmentDirections
                .actionHomeFragmentToMultiTouchGestureFragment()
        )
        binding.matTouchEventInViewGroup.navigateToDestination(
            HomeFragmentDirections
                .actionHomeFragmentToTouchEventInViewGroupFragment()
        )
        binding.matKeyboardInput.navigateToDestination(
            HomeFragmentDirections
                .actionHomeFragmentToKeyboardInputFragment()
        )
        binding.btnGoToResults.navigateToDestination(
            HomeFragmentDirections
                .actionHomeFragmentToResultsAllFragment()
        )
    }


}

fun View.navigateToDestination(directions: NavDirections) =
    setOnClickListener {
        findNavController().navigate(directions, navOptions {
            anim {
                enter = R.anim.nav_default_enter_anim
                exit = R.anim.nav_default_exit_anim
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
            }
        })
    }

