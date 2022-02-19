package com.example.advanceanimation.fragment.revealorhide

/*
There are 3 common animations to use when showing or hiding a view
    . Circular reveal animation
    . Crossfade animation
    . Card flip animation


Crossfade animation => is in the crossFadeAnimation()
    Crossfade animations (also known as dissolve) gradually fade out one View/ViewGroup while simultaneously fading in another.
    This animation is used for situations where we wanna switch content or views in our app.
    We implement this animation using ViewPropertyAnimator



Card flip animation => is in the cardFlipAnimation() which go to CardFlippedAnimatedFragment
    Card flip (varaq zdn card) animate between views of content by showing an animation that emulated(shabih sazi) a card flipping
        over. We can do it with FragmentTransaction or NavigationComponent library between fragments
    To create a card flip animation we need a total of four animators. Two animators for when the front of the card animates out
    and to the left and in and from the left. Two animator for when the back of card animates in and from the right and our and
    to the right
    Each side of card is separate layout that can contain any content we want


Circular reveal animation => is in circularRevealAnimation()
    Reveal (ashkar krdn) animation provide users visual continuity (tadavom basri) when we show or hide a group of UI elements
    The ViewAnimationUtils.createCircularReveal() method enables us to animate a clipping circle to reveal or hide a view.
    This animation provides a ViewAnimationUtils class, which is available for Android 5.0(API level 21) and higher.

    The ViewAnimationUtils.createCircularReveal() takes five parameters :
        1. The view that we want to either hide/show on the screen
        2, 3. Are the x and y coordinates for the center of the clipping circle. Typically this will be the center of the view, but
            we can also use the point the user touched so the animation starts where they selected
        4. Starting radius for clipping circle
        5. Is final radius of clipping circle
    * In circularRevealAnimation() the initial radius is set to 0 so the view to be displayed will be hidden by the circle
        The last parameter is final radius of the circle. When displaying a view make sure the final radius is larger than or
        as large as the view itself so the view can be fully revealed before the animation finished.
 */






