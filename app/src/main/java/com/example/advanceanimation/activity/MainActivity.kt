package com.example.advanceanimation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.view.WindowInsets
import androidx.core.view.*
import com.example.advanceanimation.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // To enable window content transition in our code instead, call the Window.requestFeature()
//        with(window) {
//            requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
//            enterTransition = Explode()
//            exitTransition = Explode()
//        }

        /*
        Sets whether the decor view should fit root-level content views for
        WindowInsetsCompat (describe a set of inset for window content).
        If set to false, the framework will not fit the content view to the insets and will just pass
        through the WindowInsetsCompat to the content view
         */
//        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContentView(R.layout.activity_main)

//        val windowInsetController =
//            ViewCompat.getWindowInsetsController(findViewById<ConstraintLayout>(R.id.mainContainer))
//        windowInsetController?.isAppearanceLightStatusBars = true

    }
    private fun appearLightStatusBar() {
        val windowInsetController =
            ViewCompat.getWindowInsetsController(window.decorView) ?: return
        windowInsetController.isAppearanceLightStatusBars = true
    }


    private fun hideSystemBars() {
        /*
        decorView = retrieve the top-level window decor view (containing the standard window frame/decorations
        and the client's content inside of that), which can be added as a window to the window

        getWindowInsetsController() = Retrieves a WindowInsetsControllerCompat of the window this view is attached to.
         */

        val windowInsetsController =
            ViewCompat.getWindowInsetsController(window.decorView) ?: return
        // Configure the behavior of the hidden system bars
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        // Hide both the status bar and the navigation bar
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
    }

    private fun showSystemBars() {
        val windowInsetsController =
            ViewCompat.getWindowInsetsController(window.decorView) ?: return
        // Configure the behavior of the hidden system bars
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        // Hide both the status bar and the navigation bar
        windowInsetsController.show(WindowInsetsCompat.Type.systemBars())
    }

    // Called when the main window associated with the activity has been attached to the window manager
//    override fun onAttachedToWindow() {
//        super.onAttachedToWindow()
//        appearLightStatusBar()
//    }


    /*
    Called when the current Window of the activity gains or loses focus
     */
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
//        appearLightStatusBar()
//        hideSystemBars()
    }

}