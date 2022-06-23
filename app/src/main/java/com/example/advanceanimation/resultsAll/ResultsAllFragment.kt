package com.example.advanceanimation.resultsAll

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.advanceanimation.R
import com.example.advanceanimation.databinding.FragmentHomeBinding
import com.example.advanceanimation.databinding.FragmentResultsAllBinding
import com.example.advanceanimation.navigateToDestination


class ResultsAllFragment : Fragment() {


    private lateinit var binding: FragmentResultsAllBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_results_all,
            container,
            false
        )

        onClick()

        return binding.root
    }

    private fun onClick() {
        binding.matSwipeDrawerLayout.navigateToDestination(
            ResultsAllFragmentDirections
                .actionResultsAllFragmentToSwipeDrawerLayoutFragment()
        )
        binding.matZoomAndScaleImage.navigateToDestination(
            ResultsAllFragmentDirections
                .actionResultsAllFragmentToScaleAndZoomFragment()
        )
        binding.matDarkAndNightModeCircular.navigateToDestination(
            ResultsAllFragmentDirections
                .actionResultsAllFragmentToDarkAndLightCircularActivity()
        )
        binding.matSwipeContainer.navigateToDestination(
            ResultsAllFragmentDirections
                .actionResultsAllFragmentToMainSwipeContainerActivity()
        )
        binding.matDissolveImages.navigateToDestination(
            ResultsAllFragmentDirections
                .actionResultsAllFragmentToDissolveFragment()
        )
        binding.matFabTransform.navigateToDestination(
            ResultsAllFragmentDirections
                .actionResultsAllFragmentToFabTransformFragment()
        )
        binding.matListStaggerAnimation.navigateToDestination(
            ResultsAllFragmentDirections
                .actionResultsAllFragmentToStaggerRecAnimFragment()
        )
        binding.matListReordering.navigateToDestination(
            ResultsAllFragmentDirections
                .actionResultsAllFragmentToReorderListFragment()
        )
        binding.matListSwiping.navigateToDestination(
            ResultsAllFragmentDirections
                .actionResultsAllFragmentToSwipeListFragment()
        )
        binding.matMultipleListSelection.navigateToDestination(
            ResultsAllFragmentDirections
                .actionResultsAllFragmentToMultipleSelectionListFragment()
        )
    }

}