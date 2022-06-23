package com.example.advanceanimation.activity

import android.content.Context
import android.view.View
import android.view.WindowInsetsController
import androidx.core.view.DisplayCutoutCompat
import androidx.core.view.WindowInsetsCompat

/*
By default, apps are laid out below the status bar at the top, and above the navigation bar at the bottom.
Together, the status bar and the navigation bar are called the system bars
WindowInsets, is parts of our screen where our app can intersect(taqato) with system UI

Edge-To-Edge (display content fully and behind the status and navigation bar) ->
    Implementing edge-to-edge in your app requires the following steps:
        1. Lay out our app full-screen. -> {
            This is the primary step to ensure our app goes edge-to-edge
            Use WindowCompat.setDecorFitsSystemWindows(window, false) to lay out our app behind the system bars
        }
        2. Change the system bar colors and transparency. -> {
            We have to change the color of status and navigation bar to display the content underneath to be visible
            After your app performs this step, the system handles all visual protection of the user interface in :
            - gesture navigation mode = The system applies dynamic color adaptation, in which the contents of the
            system bars change color based on the content behind them
            - and in button mode. = The system applies a translucent scrim behind the system bars (for API level 29 or higher)
             or a transparent system bar (for API level 28 or lower).
            We change this in theme.xml
            For change the content light and dark of system bar programmatically we can use WindowInsetController
            (interface to control windows that generate insets) or WindowInsetControllerCompat instead of theme.xml to control
            the status bar content color. To do so, use the setAppearanceLightNavigationBars() function, passing in true
            We use ViewCompat.getWindowInsetsController to get the WindowInsetController from the decorView
        }
        3. Handle any visual overlaps. -> {
            After we’ve implemented an edge-to-edge layout with color transparency, some of our app's views may be drawn behind
            the system bars
            We can address overlaps by reacting to insets, which specify which part of the screen intersect with system UI such as
            system bars. Intersecting means simply being displayed above the content and also inform our app about system gesture

            The type of insets that apply to displaying our app edge-to-edge are:
            . System bar insets = These insets are best used for views that are tappable and that shouldn't be visually obscured by
            system bars. -> {
                System bar insets are the most commonly-used type of insets, and represent where the system UI is displayed in the
                z-axis above our app
                They are best used to move or pad views in your app that are both tappable and shouldn't be visually obscured by
                the system bars
                To avoid this kind of visual overlap caused by edge-to-edge, we can increase the view margin by using getInsets(int)
                with WindowInsetsCompat.Type.systemBars()
                I Used this inside HomeFragment to update the btn margin from top


                For gesture system insets we use to move or pad swappable views away from edges. Common use cases include bottom sheet
                , swiping in games, and carousels implemented using ViewPager.
                On Android 10 or higher, system gesture insets contain a bottom inset for the home gesture, and a left and right inset
                for the back gestures.
                To do this we use systemGesture() instead of systemBars() for Type and updatePadding() method for view instead of
                updateLayoutParams<ViewGroup.MarginLayoutParams>
            }

            . System gesture insets = These inset apply to gesture navigational area used by system that take priority over our app
        }

        4. Immersive mode -> {
            Some content is best experience in full screen (hide system bars)
            It is in hide and show system bar function in Activity
        }


    Display cutout ->
        A display cutout is an area on some devices that extends into the display surface. It's for edge-to-edge while
        providing space for important sensors. Display cutout is for Android 9 (API 28) and higher. However display manufactures
        can choose to support display cutout in Android 8.1 and lower.
        How our app handles cutout areas:
        If we're rendering into the cutout area, we can use WindowInsetsCompat.getDisplayCutout() to retrieve a DisplayCutout object
        that contain safe insets and bounding box for each output. These API is to check whether our content is to overlap with
        the cutout so that we can reposition if needed.
        We can also determine whether content is laid out behind the cutout area. The layoutInDisplayCutoutMode window layout attr
        is to control how our content is drawn in the cutout area. We can set this to one of this values:
            . LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT = content renders into the cutout area when display in portrait mode but is
            letterboxed when is in landscape mode
            . LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES = Content renders into the cutout area in both land and port mode
            . LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER - Content never renders into the cutout area.
        Default:
        By default, in portrait mode with no special flags set, the status bar on a device with a cutout is resized to be at least
        as tall as the cutout, and our content displays in the area below. In landscape or fullscreen mode, your app window is
        letterboxed so that none of our content displays in the cutout area

        Best practice :
        Use View.getLocationInWindow() to determine how much of the window our app is taking up. Do not assume that the app has
        taken up the entire window, and do not use View.getLocationOnScreen().
 */