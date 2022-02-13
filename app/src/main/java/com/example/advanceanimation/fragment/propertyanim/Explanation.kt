package com.example.advanceanimation.fragment.propertyanim

/* The property animation system is a robust framework that allows us to animate almost everything
The property animation let us define the following characteristics of an animation :
    * Duration = it is the duration of animation. The default length is 300ms
    * Time interpolation = We can define how the value for property are calculated as a function of animation's current elapsed time
    * Repeat count and behaviour = We can specify to have animation repeat and how many time to repeat when reaches the end of duration.
    We can also specify whether we want the animation to play back in reverse
    * Animator sets = We can group animations into logical sets that play together or sequentially ( after each other ) or after specified delay
    * Frame refresh delay = We can specify how often to refresh frames of our animation. The default is set to refresh every 10ms but the speed of refresh dependent on how
    busy the system is overall and how fast the system can service the underlying timer


    ValueAnimator: provide a single timing engine for running animations which calculate animated values and set them on target object and
    keeps track of our animation's timing, such as how long the animation has been running, and the current value of the property that it is animating
    - By default ValueAnimator uses non-linear time interpolation via the AccelerateDecelerateInterpolator class, which accelerates into and decelerates out of an animation.
    This behaviour can be changed by changing interpolator
    * The ValueAnimator encapsulate a TimeInterpolator, which define animation interpolation (behaviour such as linear, non-linear ,... ) and a TypeEvaluator, which define
    how to calculate values for the property being animated ( it can be int, float, ... ). Note that the ValueAnimator calculates the elapsed fraction between 0 and 1 based on
    duration of animation and how much time has elapsed. 0 means 0% and 1 mean 100%


    * Note: The view animation system provide the capability to only animate View objects so it we wanna animate non-View object, we have to implement our own code to do so
    but with the property animation system we can animate an property of any object ( Views and non-Views ) and the object itself is actually modified


    The Animator class provide the basic structure for creating animations. We don't directly use this class besides we use the subclasses:
        * ValueAnimator : We've explained
        * ObjectAnimator : A subclass of ValueAnimator that allow to set a target object and object property to animate
        * AnimatorSet : Provide a mechanism to group animation together
    Evaluators tell the property animation system how to calculate values for a given property:
        * IntEvaluator : calculate values for int properties
        * FloatEvaluator : calculate values for float properties
        * ArgbEvaluator : calculate values for color properties that represent as hexadecimal values
        * TypeEvaluator : An interface that allow us to create our own evaluator so If we are animating an object property that is not an int, float or color we must
        implement the TypeEvaluator. We can also specify a custom TypeEvaluator for int, float and color values as well
    A time interpolator defines how specific values in an animation are calculated as a function of time. For example we can specify animation to happen linearly across the
    whole animation or specify animation non-linear time :
        * AccelerateDecelerateInterpolation : An interpolator whose rate of change start and ends slowly but accelerate through the middle
        ( درون یابی که سرعت تغییر آن به آرامی شروع و به پایان میرسد اما از وسط شتاب میگیرد )
        * AccelerateInterpolator : An interpolator whose rate of change start out slowly and then accelerate
        * AnticipateInterpolator : An interpolator whose change starts backward then flings ( پرتاب ) forward
        * AnticipateOvershootInterpolator : An interpolator whose change start backward , flings forward and overshoot ( فراتر رفتن ) the target value,
        then finally goes back to the final value
        * BounceInterpolator : An interpolator whose change bounces at the end
        * CycleInterpolator : An interpolator whose animation repeats for a specified number of cycles
        * DecelerateInterpolator : An interpolator whose rate of change starts out quickly and then decelerate
        * LinearInterpolator : An interpolator whose rate of change is constant
        * OvershootInterpolator : An interpolator whose change flings forward and overshoots the last value and come back
        * TimeInterpolator : An interface that allows us to implement our own interpolator


    We can listen for important events during an animation's duration with the listener below:
    . Animator.AnimatorListener =>
        * onAnimationStart() - Called when animation starts. (we can call doOnStart {})
        * onAnimationEnd() - Called when animation ends. (we can call doOnEnd {})
        * onAnimationRepeat() - Called when animation repeat itself. (we can call doOnRepeat {})
        * onAnimationCancel() - Called when animation is cancelled. A cancelled animation also calls onAnimationEnd() (we can call doOnCancel {})
    . ValueAnimator.AnimatorUpdateListener =>
        * onAnimationUpdate() - called on every frame of the animation
            Depending on what property or object we animating, we might need to call invalidate() on a view to force that area of the screen redraw itself (Described in fragment)
    We can extendAnimatorListenerAdapter into addLister() instead of implementing the Animator.AnimatorListener interface, if we don't wanna implement all of the method of the
        Animator.AnimatorListener interface or we can use doOn... {} (better idea)
        ObjectAnimator.ofFloat(newBall, "alpha", 1f, 0f).apply {
            duration = 250
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    balls.remove((animation as ObjectAnimator).target)
                }
            })
            or
            doOnEnd {
                balls.remove((animation as ObjectAnimator).target)
            }
        }

    * Big note: when ValueAnimator or ObjectAnimator runs they  =>
        1. calculate a current elapsed fraction of the animation (value between 0 and 1)
        2. calculate an interpolated version of that depending on what interpolator that we are using
        The interpolated fraction is what our TypeEvaluator receives through the fraction parameter


Animate Layout Changes =>
    The property animation system provides the capability to animate changes to ViewGroup objects
    We can animate layout changes within a ViewGroup with LayoutTransition class. Views inside a ViewGroup can go through appearing and disappearing animation when we add or remove
        them from a ViewGroup or when we change the View's setVisibility(). The remaining Views inside ViewGroup can also animate into their new positions when add or remove Views.
    We can define animations in a LayoutTransition object by calling setAnimator() and pass an Animator object with one of following LayoutTransition constants:
        * APPEARING - A flag indicate that animation runs on items that are appearing in the container
        * CHANGE_APPEARING - A flag indicate that animation runs on items that are changing due to new item appearing in the container
        * DISAPPEARING - A flag indicate that animation runs on items that are disappearing from the container
        * CHANGE_DISAPPEARING - A flag indicate that animation runs on items that are changing due to an item disappearing from the container
    We can define our own custom animation for these four types of events, to customize the look of our layout transition or just tell thee animation system to use default anim.
    * The LayoutAnimations shows us how to define animations for layout transition and set the animations on the View objects that we wanna animate
    =>
    The LayoutAnimationByDefault and its corresponding layout_animations_by_default.xml layout resource file shows how to enable the default transition for ViewGroup.
        We only have to enable the android:animateLayoutChanges for the ViewGroup


Animate view state change using StateListAnimator =>
    The StateListAnimator class let us define animators that runs when the state of a view changes. It behaves as a wrapper for an Animator object, calling that animation whenever
        the specified view state (such as "pressed" or "focused") changes.
    The StateListAnimator can be defined in an XML resource with a root <selector> element and child <item> elements that each specify a different view state defined by the StateListAnimator
        class.( Each <item> contains the definition for a property animation set )
    For example in `animate_scale` file creates a state list animator that change the x and y scale of the view when it's pressed.
        And to attach the state list animator to a view, add the android:stateListAnimator attr as set into button in fragment
        Or we can assign AnimatorInflater.loadStateListAnimator() method to the View.setStateListAnimator method\



Specify Keyframes =>
    A Keyframe object consists of a time/value pair which let us define a specific state at a specific time of an animation
 */

