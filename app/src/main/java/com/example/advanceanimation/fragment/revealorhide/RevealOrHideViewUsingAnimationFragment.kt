package com.example.advanceanimation.fragment.revealorhide

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.advanceanimation.R

class RevealOrHideViewUsingAnimationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(
            R.layout.fragment_reveal_or_hide_view_using_animation,
            container,
            false
        )
    }

}