package com.example.advanceanimation.touchAndInput.touchgesture

/*
Android provides a variety of APIs to help us create and detect gestures. (Adding touch-based interaction to our app can greatly
increases its usefulness(soodmandi) and appeal(jazabiat)

A "touch gesture" occurs when a user place one or more finger on the touch screen and our app interprets(mani krdn/ tafsir krdn)
that pattern of touches as a particular gesture.
There are correspondingly two phases to gesture detection:
    1. Gather data about touch events
    2. Interprets the data to see if it meets the criteria for any of the gestures our app supports

The example in this use GestureDetectorCompat and MotionEventCompat classes. These classes are in the Support Library.
MotionEventCompat is not replacement for MotionEvent class. Rather it provides static utility methods to which we pass our
MotionEvent object in order to receive the desired action associated with that event

Gather data =>
    When a user place one or more fingers on the screen, this trigger the callback onTouchEvent() on the View that receive the touch
    event. For each sequence of touch event (position, pressure, size, addition of another finger, ...) that is identified as gesture.
    onTouchEvent fires several time.
    The gesture starts when the user first touches the screen, continues as the system tracks the position of the user's finger(s) and
    ends by capturing the final event of the user's fingers leaving the screen. Throughout this interaction, the MotionEvent delivered to
    onTouchEvent() provide the detail of every interaction.

    Capture touch event for an Activity or View => is in captureTouchEvent()
    To intercept touch event in Activity or View, override the onTouchEvent() or setOnTouchListener {}

    * Beware of creating listener that returns false for the ACTION_DOWN event. If we do this the listener will not call the subsequent
    ACTION_MOVE and ACTION_UP. This is because ACTION_DOWN is the starting point for all touch event





Detect Gesture => in gestureDetector()
    Android provides the GestureDetector for detecting various gestures and events using supplied MotionEvent. Some of the gesture it
    supports include onDown, onLongPress(), onFling()

    Detect all supported gesture =>
    When we instantiate a GestureDetectorCompat, one of the parameters it takes is a class that implement the GestureDetector.OnGestureListener
    GestureDetector.OnGestureListener notifies users when a particular touch event has occurred
    We should pass our GestureDetector to our onTouchEvent() to receive events
    A return value of true from the individual on<TouchEvent> methods indicates that we have handled the touch event. A return value of
    false passes events down through the view stack until the touch has been successfully handled.


    
About MotionEvent => 
    MotionEvent describe movement in term of action code and set of axis value. The action code describe the state change that occurred
    such as pointer going down or up and axis value describe the position and other movement properties.
    When the user first touches the screen, the system deliver a touch event to appropriate View with the action code ACTION_DOWN and
    set of axis value that include the X and Y coordinates of the touch and information about pressure, size and orientation of content
    area.
    MotionEvent contain information about all of the pointers that are currently active even if some of them have not moved since the
    last event was delivered. The number of pointer only ever change by one as individual pointers go up and down, except when the gesture
    is cancelled.
    Each pointer has a unique id when it first goes down (indicate by ACTION_DOWN or ACTION_POINTER_DOWN) and it remains valid until the
    pointer eventually goes up (indicate by ACTION_UP or ACTION_POINTER_UP) or when the gesture is canceled (indicate by ACTION_CANCEL)
    * As each additional pointer that goes down or up, the framework will generate a motion event with ACTION_POINTER_DOWN or
        ACTION_POINTER_UP
    * Some pointing devices such as mice may support vertical and horizontal scrolling. A scroll event is reported as a generic motion
        event with ACTION_SCROLL that include the relative scroll offset in the AXIS_VSCROLL and AXIS_HSCROLL axes. We retrieving these
        additional axes information with getAxisValue(int)
    * More info about set of available axes and the range of motion can be obtain using InputDevice#getMotionRange. Some common joystick
        axes are AXIS_X, AXIS_Y, AXIS_HAT_X, AXIS_HAT_Y, AXIS_Z, AXIS_RZ
    * There are different input devices such as trackball devices or joystick devices and they are represent pointer coordinate
        differently for more info about pointer device = https://developer.android.com/reference/android/view/InputDevice

    The MotionEvent class provide many method to query the position and other properties such as getX(int), getY(int), getAxisValue(int),
    getPointerId(int), getToolType(int), and many others.
    -Use the getPointerId(int) to obtain a pointer id of a pointer to track it across all subsequent motion event in a gesture. Then for
    successive motion events, use the findPointerIndex(int) to obtain the pointer index for a given pointer id in that motion event.
    -The index of pointer can change from one event to the next but the pointer id is guaranteed to remain constant as long as the pointer
    remain active.

    Batching (Daste) => is in touchEventWithHistoricalCoordinates()
    For efficiency, motion events with ACTION_MOVE may batch together multiple movement sample within a single object.
    The most current pointer coordinates are available using getX(int) and getY(int). Earlier coordinate within the batch
    are accessed using getHistoricalX(int, int) and getHistoricalY(int, int).
    To process all coordinates in the batch in time order, first consume the historical coordinates then consume the current coordinates.
 */