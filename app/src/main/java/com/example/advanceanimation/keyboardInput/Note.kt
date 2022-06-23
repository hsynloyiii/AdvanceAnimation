package com.example.advanceanimation.keyboardInput

/*
The Android system shows an on-screen keyboard-known as a soft input method.
In addition to the on-screen input methods, Android also supports hardware keyboards, so it's important that our app optimized with
    them.
The soft input method appear with TextField when their focus changes


Specify the keyboard type => is for first text field in xml
        We should always declare the input method for our text field by adding the android:inputType="" attr
        For enable spelling correction :
        The android:inputType allows us to specify various behaviours for the input method
        if our text field is intended for basic text input (such for text message), we should enable auto spelling correction with
        "textAutoCorrect"
        We can combine different behaviours so in here we created a text field that capitalize the first word of a sentence and also
        auto-correct misspelling
        * If we wanna enable the text multi-line we should set textMultiLine for our input type and the button action will be return


Specify the input method action => is for the second text field in xml and inputMethodAction() in fragment
    Most input method provide a user action button in the bottom corner. By default system uses this button a Next or Done unless
    our text field allows multi-line text. However we can specify additional action that might be more appropriate for our text field
    such as Send or GO.
    To specify the keyboard action button, use the android:imeOptions with an action value such as actionSend or actionSearch

    We can listen for presses on the action button with TextView.OnEditorActionListener for EditText. The IME (Input Method Editor)
    action ID defined in the EditorInfo.
    EditorInfo = describes several attrs of a text editing object that an input method is communicating with (typically an EditText), most
    importantly the type of text content it contains, and the current cursor position.


Provide auto-complete suggestions => is for the third text field in xml and autoCompleteSuggest() in fragment
    If we wanna provide suggestion for the user as they're typing, we can use subclass of EditText called AutoCompleteTextView.
    To implement auto-complete, we must specify an Adapter that provides the text suggestion. There are several kind of adapters,
    depend of where the data is coming from (db or an array)
    In this example we use ArrayAdapter. We defined the array that contains all text suggestions in res/value/strings.xml
    In the autoCompleteSuggest() we created an ArrayAdapter which set to our AutoCompleteTextView. The ArrayAdapter bind each item
    in the countries_array to a TextView that exists in the simple_list_item_1 layout.

    ArrayAdapter = The array adapter creates a view by calling Object#toString() on each data object in the collection we provide
    and place the result in a TextView
 */