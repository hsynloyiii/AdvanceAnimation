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
 */