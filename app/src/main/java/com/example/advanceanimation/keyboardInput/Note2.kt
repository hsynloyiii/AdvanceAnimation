package com.example.advanceanimation.keyboardInput

/* Continue from last Note from the same fragment

Show the input method when the activity starts =>
    Give focus to the first text field in our layout when activity starts, doesn't show the input method because entering text
    is not primary task in the activity. However if entering text is indeed the primary task, then we probably want the input method
    to appear by default
    To show the input method when activity start add the android:windowSoftInputMode attr in <activity> in manifest and set the value
    as "stateVisible"

Show input method on demand => is in the showWhenStart()
    When we wanna ensure that the input method is visible, we can use InputMethodManager to show it
    in showSoftKeyboard(view: View) takes a View which the user should type sth, calls requestFocus() to give it focus and then call
    showSoftInput() to open the input method.

Specify how our UI should respond =>
    When the input method appears on the screen, it reduces the amount of space available for your app's UI.
    To declare we should use android:windowSoftInputMode attr in <activity> manifest

    . To ensure that the system resize our layout to available space, which ensure that all of our layout content is accessible
    (even though it probably requires scrolling)- use "adjustResize"


* When we use hardware keyboard we should specify some configuration which is in the Supporting keyboard navigation and Handling keyboard
    action section in this url =  https://developer.android.com/training/keyboard-input/navigation


Image keyboard support =>
    Users often want to communicate using emojis, stickers, and other kind of rich content. In previous version of Android, soft keyboard
    (known as input method editors or IMEs) could send only unicode emoji to apps.
    With Android 7.1 (API level 25), the Android SDK includes the Commit Content API, which provide a universal way for IMEs to send
    images and other rich content directly to a text editor in an app.
    With this API, we can build messaging apps that accept rich content from any keyboard, as well as keyboards that can send rich
    content to any app. (The Google Keyboard and apps like Google Messenger support the Commit Content API in Android 7.1.

    How its work :
    Keyboard image insertion requires participation from both the IME and the app. The following describe each step in image insertion :
    1. When the user taps on an EditText, the editor send a list of MIME content types that it accept in EditorInfo.contentMimeTypes
    2. The IME reads the list of supported types and display content in the soft keyboard that the editor can accept
    3. When the user selects an image, the IME calls commitContent() and send an InputContentInfo to the editor.
    (The commitContent() call is analogous to the commitText() call, but for rich content. InputContentInfo contains an URI that
    identifies the content in content provider)



Receive rich content => is in the receiveRichContent()
    We can attach an interface, OnReceiveContentListener (Interface that listen for inserting content, which may be text and non-text),
    to UI components and get a callback when content is inserted through any mechanism.
    The callback becomes the single place for our code to handle receiving all content (videos, images, texts, audio files, ...)
    With other existing APIs, each UI mechanism, such as the long-press menu or drag and drop, has its own API, which means we should
    integrate with each API separately, adding similar code for each mechanism that inserts content:
          Action     ==================================================================> API that must implement
    Insert using paste from long-press menu                              TextView.onTextContextMenuItem (or ActionMode.Callback)
    Insert using drag and drop                                                          TextView.onDragEvent
    Insert a keyboard image (such as sticker)                            InputConnection.commitContent (or OnCommitContentListener)
    But with OnReceiveContentListener API consolidates these different code path by creating a single API to implement (so with this
    unified API lets us support all UI mechanism). And also with this new way of inserting content, we don't need to make additional
    code changes support in our app.
    The API is a listener with a single method, OnReceiveContentListener. To support older version of the Android platform, recommend
    using the matching OnReceiveContentListener interface in the AndroidX Core library.
    How to use :
    First create a MyReceiver class. (is in KeyboardInputFragment and more explanation is in there)
    Second After implementing the listener, set it to the appropriate UI element in our app which is in receiveRichContent()


The OnReceiveContentListener API is next version of existing keyboard image API that explained in 2 above section but not completed.
 */