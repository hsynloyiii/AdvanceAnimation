package com.example.advanceanimation.fragment.moveview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.advanceanimation.R
import com.example.advanceanimation.databinding.FragmentMoveViewUsingAnimationBinding
import com.example.advanceanimation.databinding.FragmentRevealOrHideViewUsingAnimationBinding

class MoveViewUsingAnimationFragment : Fragment() {

    private lateinit var binding: FragmentMoveViewUsingAnimationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoveViewUsingAnimationBinding.inflate(inflater, container, false)

        return binding.root
    }

}