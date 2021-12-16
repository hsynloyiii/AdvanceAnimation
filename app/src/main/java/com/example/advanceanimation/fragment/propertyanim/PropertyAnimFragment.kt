package com.example.advanceanimation.fragment.propertyanim

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.advanceanimation.R
import com.example.advanceanimation.databinding.FragmentHomeBinding
import com.example.advanceanimation.databinding.FragmentPropertyAnimBinding


class PropertyAnimFragment : Fragment() {


    private lateinit var binding: FragmentPropertyAnimBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_property_anim,
            container,
            false
        )

        onClick()

        /*

         */

        return binding.root
    }

    private fun onClick() {
        binding.imageBtnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}