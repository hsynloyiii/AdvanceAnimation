package com.example.advanceanimation.touchAndInput.touchEventInViewGroup

/* Manage touch event in ViewGroup

It's common for a ViewGroup to have children that are targets for different touch events than the ViewGroup itself. To make sure
    that each view correctly receives the touch events intended for it, override the onInterceptTouchEvent() method.

The onInterceptTouchEvent() is called whenever a touch event is detected in the surface(sath) of a ViewGroup, including the on the
    surface of the children. If onInterceptTouchEvent() returns true, the MotionEvent is intercepted, meaning it's not passed on to
    the child, but rather to the onTouchEvent() method of the parent.

The onInterceptTouchEvent() gives a parent the change to see any touch event before its children do. If we return true from it,
    the child view that was previously handling touch events receives an ACTION_CANCEL, and the events from that point will send to
    the parent's onTouchEvent() for the usual handling. onInterceptTouchEvent can also return false and simply spy on events as they
    travel down the view hierarchy to their usual target , which will handle the events with their own onTouchEvent()

In the MyViewGroup in TouchEventInViewGroupFragment which extend ViewGroup =>
    It contains multiple child views. If we drag our finger across the child view horizontally, the touch view should no longer get
    touch events, and MyViewGroup should handle touch events by scrolling its content. However, if we press buttons in the child
    view, or scroll it vertically, the parent should intercept those touch events, because the child is intended target and in those
    cases, onInterceptTouchEvent() should return false, and MyViewGroup's onTouchEvent() won't be called.
    * Note that ViewGroup also provides a requestDisallowInterceptTouchEvent() and called this method when a child doesn't want the
    parent and its ancestors(ajdad) to intercept touch event with onInterceptTouchEvent()
    * If a ViewGroup receive a MotionEvent with an ACTION_OUTSIDE, the event won't be dispatched to its children by default. To process
    a MotionEvent with ACTION_OUTSIDE, either override dispatchTouchEvent(event MotionEvent) to dispatch to the appropriate view or handle it
    in the relevant Window.Callback (for example, Activity)

    In this class constructor we used ViewConfiguration to initialized a variable called mTouchSlop. We can use the ViewConfiguration to
    access common distances, speeds, and times used by Android system.
    Touch slop refers to distance in pixels a user's touch can wander before the gesture is interpreted as scrolling. Touch slop is used
    to prevent accidental scrolling when user is performing some other touch operation, such as touching on-screen element.
    Two other commonly used ViewConfiguration methods are getScaledMinimumFlingVelocity() and getScaledMaximumFlingVelocity(). These methods
    return the min and max velocity to initiate a fling, as measured in pixel per second.
    For example :
    MotionEvent.ACTION_MOVE -> {
        ...
        val deltaX: Float = motionEvent.rawX - mDownX
        if (Math.abs(deltaX) > mSlop) {
            // A swipe occurred, do something
        }
        return false
    }




Extend a child view's touchable area -> is in the extendChildViewTouchableAre()
    Android provides the TouchDelegate class to make it possible for a parent to extend the touchable area of a child view beyond the child's
    bounds. This is useful when the child has to be small, but should have a larger touch region.
    We're gonna extend the touchable are of imageBtnBack.
    The snippet in this fun does the following :
    . Gets a parent view and post a Runnable on the UI thread. This ensures that the parent lays out its children before calling the
    getHitRect() (The getHitRect() method gets the child's hit rectangle (touchable area) in the parent's coordinates)
    . Finds the ImageButton child view and calls getHitRect() to get the bounds of child's touchable area.
    . Extends the bounds of the ImageButton's hit rectangle
    . Instantiate a TouchDelegate, passing in the extended hit rectangle and the ImageButton child view as parameters.
    . Sets the TouchDelegate on the parent view, such that touches within the touch delegate bounds are routed to the child

    * The parent will receive all touch events. If the touch event occurred within the child's hit rectangle, the parent will pass the
    touch event to the child for handling.
 */