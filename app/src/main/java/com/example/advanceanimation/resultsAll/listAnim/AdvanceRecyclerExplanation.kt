package com.example.advanceanimation.resultsAll.listAnim

import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
/*
Add item animations ->
    Whenever an item changes, the RecyclerView uses an animator to change its appearance.
    This animator is an object that extends the abstract RecyclerView.ItemAnimator class.
    By default, the RecyclerView uses DefaultItemAnimator to provide the animation.
    So we define our own animator object by extending RecyclerView.ItemAnimator

For Swipe and Drag ->
    We use ItemTouchHelper class

Enable list-item selection ->
    For list selection we can use recyclerview-selection library. The library provide support for both touch and mouse
    driven selection.
    Steps:
    1. Determine which selection key type to use that identify the selected item, then build ItemKeyProvider.
    There are three key types -> {
        - Parcelable : Any Parcelable type can be used as the selection key. This is specially useful in conjunction with Uri.
        If items in our view are associated with stable content:// uris, we should use Uri for our key type.
        - String : Use string when string base stable identifier is available
        - Long : Use Long when RecyclerView's long stable ids are already in use.
    } = In here we will be using String type of key as we need string id to remove these items from DB as well as server But first
    we need to call a function in our adapter which is setHasStableIds() then set it to true which determines that each item in the
    data set can be represented with a unique identifier and implement getItemId() and set sth to be our id such as position or id
    in our data.
    Next we have to build a KeyProvider which is the bridge between our adapter and our item selector
    ( access to stable selection keys identifying items presented by a RecyclerView instance )

    2. Implement the ItemDetailsLookup : These enable the selection library to access information about RecyclerView items given a
    MotionEvent. It's a factory for ItemDetails instances that are extracted from a RecyclerView.ViewHolder instance.
    ItemDetailsLookup calls getItemDetails(MotionEvent) when it needs to access information about area and
    ItemDetailsLookup.ItemDetails (An ItemDetails implementation provides the selection library with access to information
    about a specific RecyclerView item) under MotionEvent

    3. Update item Views in RecyclerView to reflect that user has selected or unselected. We can use color state list resource or
    check isActivated in the Vi

    4. Use ActionMode to provide the user with tools to perform an action on the selection.
    ActionMode = Represents a contextual mode of the user interface. Action modes can be used to provide alternative interaction modes
    and replace parts of the normal UI until finished
    Register a SelectionTracker.SelectionObserver to be notified when selection changes. When selection is first created, start
    ActionMode to represent this to the user and provide selection-specific actions

    5. Perform any interpreted secondary actions
    At the end of the event processing pipeline, it may determine user is attempting to activate an item by tapping it or drag and drop
    or set of selected items. React to these interpretations by registering the appropriate listener

    6. Assemble tracking with SelectionTracker.Builder which for track the selection of user and we create this in our activity/fragment

    7. Include selection in lifecycle event
 */
