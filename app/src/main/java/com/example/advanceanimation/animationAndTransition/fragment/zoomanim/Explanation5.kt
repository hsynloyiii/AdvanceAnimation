package com.example.advanceanimation.animationAndTransition.fragment.zoomanim

/*
First we have to create the small and large version of that View which we wanna zoom
In the XML we created the ImageButton for clickable image thumbnail and an ImageView that displayed the enlarged view of the image

After applying our layout, setup the event handler that trigger the zoom animation. In the ImageButton click listener block we execute
    the zoom animation when the user clicks the image button

For Zooming the view => it is in the zoomImageFromThumb()
    We'll now need to animate from the normal sized view to the zoomed view. In general we need to animate from the bounds of normal-sized
    view to bounds of the larger-sized view.

    In this method it shows how to implement zoom animation that zooms from an image thumbnail to an enlarged view by doing following things:
        1. Assign the high-res image to the hidden zoomed-in (enlarged) ImageView.
        2. Calculate the starting and ending bounds for the ImageView
        3. Animate each of the four positioning and sizing properties X, Y, (SCALE_X, SCALE_Y) simultaneously, from the starting bounds
            to the ending bounds. These four animation are added to the AnimatorSet so they can be started at the same time
        4. Zoom back out by running similar animation but in reverse when the user touches the screen when the image is zoomed in
 */