package com.example.advanceanimation.touchAndInput.multiTouchGesture

/*
Multi touch gesture is when multiple pointers (fingers) touch the screen at the same time

Track multiple pointers => is in the trackMultiplePointers()
    When multiple pointers touch the screen at the same time, the system generates the following touch events :
    . ACTION_DOWN = For the first pointer that touch the screen. This start the gesture. The pointer data for this pointer is always
    at index 0 in MotionEvent.
    . ACTION_POINTER_DOWN = For extra pointers that enter the screen beyond the first. The pointer data for this pointer is at index
    returned by getActionIndex()
    . ACTION_MOVE = A change has happened during a press gesture
    . ACTION_POINTER_UP = Sent when a non-primary pointer goes up
    . ACTION_UP = Sent when the last pointer leaves the screen

    We keep track of individual pointer within a MotionEvent via each pointer index and ID :
    . Index = A MotionEvent stores information about each pointer in an array. The index of a pointer is its position within this array.
    . ID = Each pointer also has an ID mapping that stays persistent across touch events to allow tracking an individual pointer across the
    entire screen

    * The order in which individual pointers appears is undefined. Thus the index of a pointer can change from one event to the next, but
    the pointer ID of a pointer is guaranteed to remain constant as long as the pointer remains active.
    * Use the getPointerID() to obtain a pointer's ID to track the pointer across MotionEvent in a gesture. Then for successive MotionEvents
    use findPointerIndex() to obtain the pointer index for a given pointer ID.



getActionMasked() =
We should always use getActionMasked() (or better yet, the compatibility version MotionEventCompat.getActionMasked()) to retrieve
the action of MotionEvent. Unlike the older getAction(), getActionMasked() is designed to work with multiple pointers. It returns the
masked action being performed, without including the pointer index bits. We can use getActionIndex() to return the index of the pointer
associated with action



Drag and Scale :
Drag an object => is in the draggingObject()
    If we're targeting android 3.0 and higher, we can use built-in drag-and-drop event listener with View.OnDragListener.
    (which I cover later)
    * With ACTION_POINTER_DOWN/UP we have to manage the object when the additional finger goes down to screen and the first finger get
    lifted, it should regard the second pointer as the default and move the image to that location that's how our app must track individual
    pointers (original fingers).
    So in the ACTION_POINTER_UP we should extracts this index and ensure that active pointer ID is not referring to a pointer that is
    no longer touching the screen. If it is, the app selects a different pointer to be active and saves its current X and Y position.
    Since this saved position is used in ACTION_MOVE case to calculate the distance (from the last X and Y to the new) to move the
    onscreen object, the app always calculate the distance to move using data from the correct pointer.

    In this method enables the user to drag an object around on the screen. It records the initial position of the active pointer,
    calculate the distance pointer traveled, and moves the object to the new position. And it's currently manages the possibility of
    additional pointer as described above.

Drag to pan =>
    Another common scenario is panning, which is when a user's dragging motion causes scrolling in both the x and y axes.
    In here we use onScroll() in GestureDetector.SimpleOnGestureListener for common gesture
    * onScroll() is called when a user dragging a finger to pan the content. onScroll() is called only when a finger is down, as soon as
    the finger is lifted from the screen, the gesture either ends, or a fling gesture is started (if the finger was moving with some
    speed just before it was lifted)\


Use touch to perform scaling => is in the touchToScale()
    GestureDetector heps us detect common gestures used by Android such as scrolling, flinging, and long press ,...
    But for scaling Android provide ScaleGestureDetector.
    Also GestureDetector and ScaleGestureDetector can be used together when we wanna a view to recognize additional gesture.
    To report detected gesture, ScaleGestureDetector uses ScaleGestureDetector.OnScaleGestureListener and
    ScaleGestureDetector.SimpleOnScaleGestureListener (when we don't care about all reported events)
 */