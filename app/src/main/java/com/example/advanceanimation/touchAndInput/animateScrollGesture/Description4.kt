package com.example.advanceanimation.touchAndInput.animateScrollGesture

/*
Implementing custom scroller should only be necessary for special scenarios and in this lesson describes such scenarios: Displaying a scroll
    effect in response to touch gesture using scrollers.
Our app can use scrollers(Scroller or OverScroller) to collect data needed to produce a scrolling animation in response to a touch event.

Scroller and OverScroller are almost the same but OverScroller also include method for indicating that users reached the content edge after a
pan or fling gesture (So it has the ability to overshoot the bounds of scrolling operation) and in most cases this class is drop-in
replacement for Scroller. Recommended to use OverScroller rather than Scroller for scrolling animations. OverScroller provide the best
backward compatibility(sazegari) with older devices.
A scroller is used to animate scrolling over time, using platform-standard physics such as friction, velocity and ...
* We generally only need to use scrollers when implementing scrolling ourselves.
    . Start for Android 12 (API 31), the visual element stretch and bounce back on drag event, and fling and bounce back on a fling event
    . On Android 11 (API 30) and lower, the boundaries display a "glow"(tabesh) effect after a drag or fling gesture to the edge
    * It uses the EdgeEffect class (actually EdgeEffectCompat class) to display these overscroll effects.


Understand scrolling terminology(loqat):
    Scrolling is general process of moving viewport. When scrolling is in both the x and y axes, it's called panning. We have two different
    scrolling, dragging and flinging:
    . Dragging : Occurs when a user drag their finger across touch screen. Simple dragging is often implemented by overriding
    onScroll() in GestureDetector.OnGestureListener.
    . Flinging : Occurs when a user drag and lift (boland krdn) their finger quickly. After the user lift their finger, we generally want to
    keep scrolling (moving the viewport), but decelerate until the viewport stops moving. Flinging can be implemented by overriding onFling()
    in GestureDetector.OnGestureListener and by using a scroller object.
    . Panning : When scrolling along both X and Y axes, it's called panning


Component contain built-in support for scrolling and overScrolling behaviour :
    . RecyclerView     . ListView     . GridView     .ScrollView     . NestedScrollView     . HorizontalScrollView     . ViewPager
    . ViewPager2


Create a custom touch-based scrolling implementation => is in the InteractiveChart-AS project
    This describe how to create our own scroller, if our app uses a component that doesn't contain built-in support for scrolling and
    overScrolling.
    The following snippet comes from InterActiveChart sample provided with this class. It uses a GestureDetector.SimpleOnGestureListener
    method onFling(). If uses OverScroller to track the fling gesture. If the user reach the content edge after perform fling gesture, the
    container indicate that the user has reached the end of the content. This indication is depend on the version of Android:
        . On Android 12 and higher, the visual elements stretch and bounce back
        . On Android 11 and lower, the visual elements display a "glow" effect


BoundEdgeEffect in recyclerview => is in the bounceEffectRecyclerview()
    We achieved the bounce with physic-based animations. We implement bounce animation with SpringAnimation
    We detect scroll action with EdgeEffect class

 */
