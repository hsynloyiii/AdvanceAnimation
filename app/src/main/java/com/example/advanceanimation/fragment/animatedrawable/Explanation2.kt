package com.example.advanceanimation.fragment.animatedrawable

/*
Here is when we wanna animate several images or if we want one icon to morph into another after user's
    action
The first option is to use an Animated Drawable which allows us to specify several static drawable
    files that will display one at a time to create an animation
The second option is to use Animated Vector Drawable which let us to animate the properties of vector drawable

AnimationDrawable(This class is basis for Drawable animations) => is in the animationDrawable()
    One way is to load series of Drawable one after each other to create an animation.
    This is traditional animation in the sense that it is created with a sequence of different images,
    played in order like a roll of film.
    We can define frames in our code and also use <animation-list> and add <item> as series of child that
    each define a frame in our res/drawable (which is rocket_thrust) directory of our project which both
    declared in animationDrawable()
    In this rocket_thrust we just run for 3 frames and by setting android:oneshot attr of the list to true
    , it will cycle just once and then stop and hold the last frame. If it false the animation will loop.
    This can be add as background image to a View and then called to play. The fully example is in animationDrawable()

    * start() cannot be called in onCreate() of activity because the AnimationDrawable is not fully attached to window
        If we wanna play animation immediately we must call start() from the onStart() which get called when Android
        makes the View visible on screen



Animated Vector Drawable => is in the animatedVectorDrawable()
    A vector drawable is a type of drawable that is scalable without getting pixelated or blurry
    The AnimatedVectorDrawable (and AnimatedVectorDrawableCompat for backward-compatibility) lets us animate
    the properties of vector drawable, such as rotating it or changing path data to morph it into a different image

    We define it in 3 XML files:
        . A vector drawable with <vector> element in res/drawable
        . An animated vector drawable with <animated-vector> element in res/drawable which refer to groups and paths by their name
        . One or more object animators with <objectAnimator> element in res/animator
    Animated vector drawables can animate the attribute of <group>(define a set of path or subgroups) and
    <path>(define paths to be drawn) elements.
    When define vector drawables which we wanna animate we have to use android:name attr to assign a unique name to groups
    and paths so we can refer to them from our animator definitions

    That what I used in vector_drawable , animator_vector_drawable in res/drawable and rotation(This animator rotation group 360degrees) and
    path_morph(This morph the vector drawable's path from one shape to another. Both path must be compatible for morphing. They must have the
    same number of commands and the same number of parameter for each commands) which declared in res/animator
 */
