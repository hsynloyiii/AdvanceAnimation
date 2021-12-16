package com.example.advanceanimation.fragment.propertyanim

/* The property animation let us define the following characteristics of an animation :
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


 */

