package com.example.advanceanimation.touchAndInput

/*
- When a view such as Button is touched. the onTouchEvent() is called on that object. However in order to intercept this,
we must extend the class and override the method but not practical on every view (massive bunch of code will come).
This is why the View class also contains a collection of nested interfaces with callbacks that we can much more easily define.
These interfaces called `event listener`, that are our ticket to capturing the user interaction with out UI.

- While we'll more commonly use the event listener to listen for user interaction, there may come a time when we do want to
extend a View class to make something more fancy. In this case, we'll be able to define the default event behaviour for our class
using the class `event handlers`


Event Listeners =>
    An event listener is an interface in the View class that contains a single callback method. These methods will be called
    when the View to which the listener has been registered is triggered by user interaction with the item in the UI
    Included in the event listener interface are the following callback method :
    - onClick() = From View.OnClickListener. This is called when the user either touches the item
    - onLongClick() = From View.OnLongClickListener. This is called when the user either touches and hold the item
    - onFocusChange() = From View.OnFocusChangeListener. This is called when the user navigates onto or away from the item, using
    the navigation-keys or trackball.
    - onKey() = From View.OnKeyListener. This is called when the user is focused on the item and presses or releases a hardware key
    on the device
    - onTouch() = From View.OnTouchListener. This is called when the user perform an action qualified as a touch event, including
    a press, a release, or any movement gesture on the screen (within the bounds of the item)
    - onCreateContextMenu() = From View.OnCreateContextMenu. This is called when a context menu is being built


Event handlers =>
    If we're building custom component from View, then we'll be able to define several callback methods used as default event handler
    Some of the common callbacks used for event handling, including:
    - onKeyDown(int, KeyEvent) = Called when a new key event occurs.
    - onKeyUp(int, KeyEvent) = Called when a key up event occurs.
    - onTrackballEvent(MotionEvent) = Called when a trackball motion event occurs.
    - onTouchEvent(MotionEvent) = Called when a touch screen motion event occurs.
    - onFocusChange(boolean, int, Rect) = Called when the view gains or loses focus.

    When managing more complex events inside a layout, consider these other methods:
    . Activity.dispatchTouchEvent(MotionEvent) = This allows Activity to intercept all touch event before they are dispatched to the window
    . ViewGroup.onInterceptTouchEvent(MotionEvent) = This allows a ViewGroup to watch event as they are dispatched to child views
    . ViewParent.requestDisallowInterceptTouchEvent(boolean) = Call this upon a parent View to indicate that it should not intercept touch
    event with onInterceptTouchEvent(MotionEvent)

* Android will call event handler first. As such returning true from these event listeners will stop the propagation of the event to other
event listener and will also block the callback to the default event handler in the View.


Touch mode =>
    For touch-capable device, once the user touches the screen, the device will enter touch mode. From this point onward, only Views for
    which isFocusableTouchMode() is true will be focusable, such as text editing widget. Other Views that are touchable, like buttons,
    will not take focus when touched; they will simply fire their on-click listeners when pressed
    Any time a user hits a directional key or scrolls with a trackball, the device will exit touch mode and find a view to take focus,
    now the user may resume interacting with UI without touching the screen
    The touch mode state is maintained throughout the entire system. To see the current state we can call isInTouchMode() to see whether
    the device is currently in touch mode.



Handling focus =>
    This include changing the focus as View are removed or hidden, or as new Views become available. View indicate their willingness(tamayol)
    to take focus through the isFocusable() method. To change whether a View can take focus call setFocusable().
    When in touch mode we may query whether a View allows focus with isFocusableInTouchMode() and we can change this with
    setFocusableInTouchMode()

    Focus movement is based on an algorithm finds the nearest neighbor in a given direction. In this situation we can provide explicit
    override with XML attrs in layout file: nextFocusDown, nextFocusLeft, nextFocusRight and nextFocusUp. Add one of this attr to the
    View from which the focus is leaving. Define the value of attr to be the id of the View to which focus should be given

    When we wanna declare focusable in our UI for a View that traditionally is not add the android:focusable XML attr to the View and
    set it to true. We can also declare a View as focusable while in Touch Mode with android:focusableInTouchMode
    To request particular view to take focus, call requestFocus()
    To listen for focus event (be notified when a View receives or loses focus), use onFocusChange()




 */
