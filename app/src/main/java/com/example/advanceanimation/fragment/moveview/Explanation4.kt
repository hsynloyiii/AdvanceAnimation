package com.example.advanceanimation.fragment.moveview

/*
Objects on screen will often need to repositioned due to user interaction or some processing behind the scenes
    instead of immediately updating the objects position, we should use an animation to move it from the starting to its end position
    Android provides ways to reposition our view objects on screen such as the ObjectAnimator, we can provide the end position we want
    the object to settle on (mostaqar shvd)


Change view position with ObjectAnimator => is in the withObjectAnimator()
    The ObjectAnimator provides an easy way to change the properties of a view (as we learned before)



Add curved motion => is in the objectAnimatorWithCurvedMotion()
    ObjectAnimator by default will reposition the view using a straight line between the starting and ending points.
    Material design relies on  curves for not only the timing of an animation but also special movement of objects on the screen

    Use PathInterpolator :
    The PathInterpolator class is a new interpolator introduced in Android 5.0 (API 21). It is base on Bezier curved or Path object.
    This interpolator specifies a motion curve in 1x1 square, with anchor points at (0,0) and (1,1)
    One way to create a PathInterpolator is by creating a Path object and supplying it to PathInterpolator
    Also we can create a pathInterpolator in XML resource in res/interpolator




//---------------------------------------- Fling ---------------------------------------------------

Move views using a fling (partab) animation => is in the moveViewsWithFlingAnimation()
    Fling-based animation uses a friction force (niro estehkak) that is proportional(monaseb) to an object's velocity.
    It has an initial momentum which is mostly received from the gesture velocity and gradually(be tadrij) slow down, so the animation
    comes to an end when the velocity of the animation is low enough that is make it no visible changes on the device screen

    * This fling animation and the animate movement using spring physics(that we learn later) is a physics-bases motion (talked in bottom
    of page)

    To use the physics-based animations, we must add AndroidX library to our project:
    implementation("androidx.dynamicanimation:dynamicanimation:1.0.0")
    The FlingAnimation object let us create a fling animation for an object

    Set velocity : To set the velocity, we call the setStartVelocity() method and pass the velocity in pixel per second.
    The starting velocity defines a speed at which an animation property changes at the beginning of the animation. The default starting
    velocity is set to zero pixel per second
    We can use a fixed value as the starting velocity or we can base it off of the velocity of a touch gesture, If we choose to provide
    a fixed value we should define the value in dp second then convert it to pixel per second. and for touch gesture we can use
    GestureDetector.OnGestureListener and the VelocityTracker classes to retrieve and compute the velocity of touch gesture.
    For converting dp per second to pixels per second we should use TypedValue:
        val pixelPerSecond: Float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpPerSecond, resources.displayMetrics)

    Set an animation value range :
    We can set the min and max animation values when we wanna restrain(mahar krdn) the property value to a certain range. This range
    control is useful when we animate properties which have an intrinsic range(mahdode zati) such as alpha(from 0 to 1)
    * When value of a fling animation reaches the minimum and maximum value, the animation ends
    To set the min and max, call the setMinValue() and setMaxValue()

    Set friction(estehkak) :
    The setFriction() let us change the animation's friction. It defines how quickly the velocity decreases in an animation.
    * If we don't define it will use the default friction which is a value of 1

    Set the minimum visible change :
    When we animate a custom property that isn't defined in pixels we should set the minimal change of animation value that is visible
    to users. It determines a reasonable threshold (astane maaqol) for ending the animation.
    It's not necessary when animating DynamicAnimation.ViewProperty because the minimum visible change is derived from the property :
        . The default minimum visible change value is 1 pixel for view properties such as TRANSLATION_X, TRANSLATION_Y, TRANSLATION_Z, SCROLL_x, SCROLL_Y
        . The default minimum visible change value is MIN_VISIBLE_CHANGE_ROTATION_DEGREES or 1/10 pixel for view properties such as ROTATION, ROTATION_X, ROTATION_Y
        . The default minimum visible change value is MIN_VISIBLE_CHANGE_ALPHA or 1/256 for animation that use opacity
    To set it call setMinimumChange() and pass either one of the minimum visible constants or a value that we need to calculate for a
    custom property.
    * We need to pass a value when we animate a custom property that is not defined in pixels.
    * To calculate minimum value for a custom property use the following formula:
        Minimum visible change = Range of custom property value / Range of animation in pixels



Physics-based animation =>
    Whenever possible our animation should apply real-world physics so they are natural-looking, For example they should maintain
    momentum when their target changes and make smooth transition during any changes. Android support library includes physics-based anim
    Two common physics-based animations:
        . Spring Animation
        . Fling Animation

    Animation not based on physics such as ObjectAnimator are static and have a fixed duration. If the target value change we need to
    cancel the anim at the time of target value change, then re-configure the animation with a new value as the new start value, then
    add the new target value. This process creates an abrupt(nagahani) stop in the animation.
    Whereas, animation built with physics-based animation APIs such as DynamicAnimation are driven by force(niro). The change in target
    value results in a change in force. The new force applies on the existing velocity , which makes a continues transition to the new
    target. This process results in a more natural-looking animation
 */