package com.example.advanceanimation.animationAndTransition.fragment.customTransition

/*
A custom transition enable us to create an animation that is not available from any of built-in transition classes.
A custom transition like built-in types, applies animations to child views of both starting and ending scenes. However we need to
    provide the code that captures property values and generates animations


Extend the Transition class => It's in CustomTransition class
    First we have to extend the Transition() class

Capture view property values =>
    Transition animations use the property animation system
    Property animations change a view property between a starting and ending value over a specified period of time so
    the framework need to have both starting and ending value of the property to construct the animation
    Since the property values needed for an animation are specified to a transition, the transition framework does not
    provide every property value to transition, Instead the framework invokes callback functions that allow a transition
    to capture only the property values it needs and store them in the framework.

    Capture starting values :
    To pass the starting value to the framework, implement the captureStartValues(transitionValues) function.
    The framework call this function for every view in the starting scene.
    The function arg is TransitionValues object that contains a reference to the view and a Map instance in which
    we can store the view values we want. In our implementation, retrieve these property values and pass them back to the framework
    by storing them in the map.
    To ensure the ket for a property value does not conflict with other TransitionValues keys use the following scheme:
    package_name:transition_name:property_name

    Capture ending values :
    The framework calls the captureEndValues(TransitionValues) function once for every target view in ending scene.
    captureEndValues work same as captureStartValues so both invoke the captureValues() to retrieve and store values (The view property
    that captureValues() retrieve is the same but it has different values in the starting and ending scenes so the framework maintains
    separate maps for the starting and ending states of a view)

Create a custom animator =>
    To animate the changes to a view between its state in the starting and ending scene we can provide by overriding createAnimator()
    When the framework call this function, it passes in the scene root view and the TransitionValues objects that contain the starting and
    ending values we captured
    To implement this function when we create a custom transition, use the view property values we captured to create an Animator object and
    return it to the framework
 */