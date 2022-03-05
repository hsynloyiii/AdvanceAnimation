package com.example.advanceanimation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Explode
import android.view.Window
import com.example.advanceanimation.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // To enable window content transition in our code instead, call the Window.requestFeature()
        with(window) {
            requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
            enterTransition = Explode()
            exitTransition = Explode()
        }

        setContentView(R.layout.activity_main)

    }
}