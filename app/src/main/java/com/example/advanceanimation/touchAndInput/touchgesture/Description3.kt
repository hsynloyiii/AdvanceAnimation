package com.example.advanceanimation.touchAndInput.touchgesture

// This is also for TouchGestureFragment
/* Track touch and pointer movement
ACTION_MOVE triggered whenever the current touch contact (taqir krdn) position, pressure, or size changes.
Detecting touch event is often based more on movement than on simple contact.
To help app distinguish between movement-based gestures(such as swipe) and non-movement gestures(such as single tap), Android includes
    the notion of(mafhomi as) "touch slop".
    Touch slop refers to the distance in pixels a user's touch can wander(To walk somewhere with no particular sense of direction or purpose)
    before the gesture is interpreted as a movement-based gesture.

There are several different ways to track movement in a gesture, depending on the needs of our app:
    . The starting and ending position of a pointer (for example, move an on-screen object from point A to point B)
    . The direction the pointer is traveling in, as determined by the x and y coordinates.
    . History. We can find a size of a gesture's history by calling the MotionEvent method getHistorySize()
    We can obtain the position, sizes, time, and pressure of each of the historical events by using the motion event's getHistorical<Value>
    methods. History is useful when rendering a trail(donbale) of the user's finger, such as for touch drawing.
    . The velocity of the pointer as it moves across the touch screen


Track Velocity => Is in the velocityTracker()
    Velocity often is a determining factor(Amel tayin konande) in tracking a gesture's characteristics(vizhegiha) or even deciding whether
    the gesture occurred(rokh dadn).
    To make the velocity calculation easier, Android provides the VelocityTracker class.
    VelocityTracker helps us for tracking the velocity of touch events. Use obtain() to retrieve a new instance of the class when we're
    gonna begin tracking. Put the MotionEvent we receive into it with addMovement(event). When we wanna determine the velocity call
    computeCurrentVelocity(int) and then call getXVelocity(int) and getYVelocity(int) to retrieve the velocity for each pointer id




Use pointer capture => Is in the capturingPointer()
    Some app, such as games, remote desktop, and virtualization client, greatly benefit from getting control over the mouse pointer.
    Pointer capture is a feature available in Android 8.0 (API level 26) and later that provide such control by delivering all mouse
    events to a focused view in our app.

    Request pointer capture =
    A view in our app can request pointer capture only when the view hierarchy that contains it has focus. So we should request pointer
    capture when there's a specific user action on the view during onClick or onWindowFocusChanged() event.
    To request pointer capture call the requestPointerCapture() method on view.
    * Once the request to capture the pointer is successful, Android call onPointerCaptureChange(true). The system delivers the mouse event
    to the focused view in our app as long as it's in the same view hierarchy as the view that requested the capture. Other app stop
    receiving the mouse event until the capture is released, including the ACTION_OUTSIDE (A movement has happened outside of the normal
    bounds of the UI element)


    Handle Capture pointer events =
    After the view successfully acquired (be dast avardan) the pointer capture, Android start delivering the mouse event.
    Our view can handle events by performing one of the following tasks :
        . If we're using custom view, override onCapturedPointerEvent(MotionEvent)
        . Otherwise, register an OnCapturedPointerListener
    When we use custom view or register a listener, our view receive a MotionEvent with pointer coordinates that specify relative movement
    such as X/Y deltas. We can retrieve the coordinate by using getX() and getY()


    Release pointer capture =
    The view in our app can release the pointer capture by calling releasePointerCapture()
    Also the system can take the capture away from our view, commonly because the view has lost focus.
 */