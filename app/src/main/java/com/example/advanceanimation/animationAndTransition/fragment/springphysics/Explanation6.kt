package com.example.advanceanimation.animationAndTransition.fragment.springphysics

/* Animate movement using spring(fanari) physics => is in the springAnimation()
Physics-based motion is driven by force. Spring force is one such force that has the following properties:
    damping and stiffness (mirayi o sefti).
In a spring-based animation, the value and the velocity are calculated based on the spring force that are applied in each frame.
In a spring-based animation, the SpringForce class class let us customize the:
    1. spring's stiffness => The stiffer the spring is, the harder it is to stretch it, the faster it undergoes dampening
        * Call the getSpring() and then setStiffness() for setting to it
        There are following stiffness constants available in the system =>
        . STIFFNESS_HIGH = 10000.0
        . STIFFNESS_MEDIUM  1500.0
        . STIFFNESS_LOW = 200.0
        . STIFFNESS_VERY_LOW = 50.0

    2. spring's damping ratio (nesbat mirayi fanari) => Describe how oscillations(navasanat) in a system decay(tahlil miravand (fased))
    after a disturbance (ekhtelal). When damping ratio :
        . > 1 (over damped) = the object quickly return to rest position without overshooting
        . = 1 (critically (enteqadi) damped) = the object will return to equilibrium(taadol) within the shortest amount of time
        . < 1 (under-damped) = trends (tamayol) to overshoot and return and overshoot again
        . = 0 (without damping) = the mass will oscillate(navasan) forever
        * To add the damping ratio call the getSpring and then setDampingRation in SpringAnimation
        There are following damping ration constant available in system =>
        . DAMPING_RATIO_HIGH_BOUNCY = 0.2
        . DAMPING_RATIO_MEDIUM_BOUNCY = 0.5
        . DAMPING_RATIO_LOW_BOUNCY = 0.75
        . DAMPING_RATIO_NO_BOUNCY = 1

    3. and its final position

    We can create a custom spring force and use as default so other SpringAnimations in app can use it as default SpringForce :
    1. Create a SpringForce object val force = SpringForce()
    2. Assign the properties such as DampingRatio, Stiffness and FinalPosition
    3. Then in the SpringAnimation call the setSpring and assign our SpringForce to it

As soon as animation begin the spring force updates the animation value and the velocity on each frame. The animation continues
until the spring force reaches an equilibrium.

General step for building a spring animation =>
    . Add the support library
    . Create a spring animation : The primary step is to create an instance of the SpringAnimation class and set the motion behaviour param
    . (Optional) Register listener : To watch for animation lifecycle changes and animation value updates which is provided two listener
        OnAnimationUpdateListener and OnAnimationEndListener which listen to update in animation value and when it comes to end.
    . (Optional) Remove listener : Its for stop receiving animation update and end callbacks, call removeUpdateListener and
        removeEndListener methods.
    . (Optional) Set a start value : Customize the animation start value. It will use the current object's property if we don't pass
        the start value
    . (Optional) Set a value range : Set it to restrain(mahar krdn) values within the min and max range. To set minimum value,
        call the setMinValue() and for maximum call the setMaxValue()
    . (Optional) Set a start velocity which can define the speed at which the animation property changes at the beginning of the anim
        (the full explanation is in `Explanation4`)
    . (Optional) Set spring properties : Set the damping ratio and the stiffness
    . (Optional) Create custom spring : in case we don't intend to use the default spring or what to use a common spring throughout the anim
    . Start animation
    . (Optional) cancel animation : cancel it in case the user abruptly(nagahan) exits the app or the view become invisible


The spring-based animation can animate views on the screen by changing the actual properties in the view objects:
    . ALPHA
    . TRANSLATION_X, TRANSLATION_Y, TRANSLATION_Z : describe where the view is located as a delta from its left coordinate,
        top coordinate, and depth of view relative to it elevation.
    . ROTATION, ROTATION_X, ROTATION_Y : control the rotation in 2D and 3D around pivot point
    . SCROLL_Y, SCROLL_X : indicate the scroll offset of the source left and top edge in pixels and also indicate the position in terms
        how much page is scrolled
    . SCALE_Y, SCALE_X : control 2D scaling around its pivot point
    . X, Y, Z : which is a sum of the left value and TRANSLATION_X, sum of the top value and TRANSLATION_Y and sum of the elevation
        value and TRANSLATION_Z


To start animation =>
    There are two ways to start the SpringAnimation :
    . By calling start()
    . By calling animateToFinalPosition(finalPosition: Float) : this set the final position of the spring and start the anim if it has
     not started yet.
     This can be more useful for chained SpringAnimation which we already declare. By using this method we don't worry if the animation
     we want to update next is currently running

 To cancel animation =>
    We can cancel or skip to end of the animation. The ideal situation is when a user exist an app abruptly or the view become
    invisible.
    . cancel() : terminate the animation at the value where it is
    . skipToEnd() : skip the animation to the final value and then terminates it
        Before calling this first we should check the state of the spring. If the state is undamped(=0) the animation can never reach the
        rest position. To chech the state we can call the canSkipToEnd() which return true if the spring is damped.
 */