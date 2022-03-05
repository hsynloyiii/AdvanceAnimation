package com.example.advanceanimation.activity

/* Start an activity using an animation
We can specify custom animations for enter and exit transitions and for transitions of shared elements between activities =>
    . An enter transition determines how views in activity enter the scene
    . An exit transition determines how views in an activity exit the scene
    . A shared element transition determines how views that are shared between two activities. If two activity have the same
    image in different position and sizes the changeImageTransform translates and scales the image smoothly between these activities

Android support these enter/exit transition =>
    . explode = Moves views in or out from the center of the scene
    . slide = Moves views in or out from one of the edges of the scene
    . fade = Adds or removes a view from the scene by changing the opacity
Android support these shared elements transition =>
    . changeBounds = Animates the changes in layout bounds of target view
    . changeClipBounds = Animates the change in clip bounds of target view
    . changeTransform = Animates the changes in scale and rotation of target view
    . changeImageTransform = Animates changes in size and scale of target view

* Activity transition APIs are available on Android 5.0 (API 21)
* We can enable windowActivityTransition and enter/exit transition in theme.xml and also with code
 */