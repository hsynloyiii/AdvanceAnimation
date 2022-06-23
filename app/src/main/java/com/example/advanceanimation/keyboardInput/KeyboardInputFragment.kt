package com.example.advanceanimation.keyboardInput

import android.app.Activity
import android.content.ClipData
import android.content.ClipDescription
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.util.Pair
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.util.component1
import androidx.core.util.component2
import androidx.core.view.ContentInfoCompat
import androidx.core.view.OnReceiveContentListener
import androidx.core.view.ViewCompat
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.advanceanimation.R
import com.example.advanceanimation.databinding.FragmentKeyboardInputBinding


class KeyboardInputFragment : Fragment() {


    private lateinit var binding: FragmentKeyboardInputBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentKeyboardInputBinding.inflate(inflater, container, false)

        onClick()

        inputMethodAction()

        autoCompleteSuggest()

        receiveRichContent()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showWhenStart()
    }

    private fun inputMethodAction() {
        binding.editTextCustomIMEOption.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_SEND -> {
                    Toast.makeText(context, "Sent", Toast.LENGTH_SHORT).show()
                    val inputMethodManager =
                        activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
                    true
                }
                else -> false
            }
        }
    }

    private fun autoCompleteSuggest() {
        // Get the string array
        val countries: Array<out String> = resources.getStringArray(R.array.countries_array)
        // Create the ArrayAdapter and set it to the AutoCompleteTextView
        ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            countries
        ).also { adapter ->
            binding.autoCompleteTextView.setAdapter(adapter)
        }
    }

    private fun showWhenStart() {
        showSoftKeyboard(editText = binding.editTextShowWhenStart)
    }

    private fun showSoftKeyboard(editText: View) {
        if (editText.requestFocus()) {
            val imm =
                activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    private fun receiveRichContent() {
        ViewCompat.setOnReceiveContentListener(
            binding.editReceiveRichContent,
            MyReceiver.MIME_TYPES,
            MyReceiver(context = requireContext(), imageView = binding.imageViewReceivedRichContent)
        )
    }

    private fun onClick() {
        binding.imageBtnBack.setOnClickListener { findNavController().popBackStack() }
    }

}

private class MyReceiver(
    private val context: Context,
    private val imageView: ImageView
) : OnReceiveContentListener {
    // To use the API, implement the listener by specifying what types of content our app can handle
    companion object {
        val MIME_TYPES = arrayOf("image/*", "video/*")
    }

    // After specifying all the content MIME types that our app can support, implement the rest of listener
    /* ContentInfoCompat = Holds all the relevant data for a request to OnReceiveContentListener. This is a backward-compatible
     wrapper for the platform class ContentInfo.
     */
    override fun onReceiveContent(view: View, payload: ContentInfoCompat): ContentInfoCompat? {
//        val split: Pair<ContentInfoCompat, ContentInfoCompat> = payload.partition {
//            it.uri != null
//        }
//        val uriContent: ContentInfoCompat? = split.first
//        val remaining: ContentInfoCompat? = split.second

        /*
        This function classifies the content and organizes it into a pair, grouping the items that matched vs didn't match
        the predicate.
        Except for the ClipData items, the returned objects will contain all the same metadata as this ContentInfoCompat.

        parameters = itemPredicate - The predicate to test each ClipData.Item to determine which partition to place it into.
        return = A pair containing the partition content, the pair first(uriContent) will have the content that matches the predicate
            or null if non of the item matches (that's why we check for non-nullability), the pair second(remaining) will have the
            content that didn't match the predicate

        ClipData = Representation of a clipped data on the clipboard
        ClipData.Item = Description of a single item in a ClipData (We will cover more later in UI project)
         */
        val (uriContent, remaining) = payload.partition {
            it.uri != null
        }
        if (uriContent != null) {
            // App-specific logic to handle the URI(s) in uriContent
            /*
            clip is data to be inserted
            linkUri = Optional http/https URI for the content that may be provided by the IME.
             */
            val item = payload.clip.getItemAt(0)
            val imageUri = uriContent.linkUri ?: item.uri
            Log.i("KeyboardInputFragment", "onReceiveContent item clip: $item")
            Log.i("KeyboardInputFragment", "onReceiveContent imageUri: $imageUri")
            Glide.with(context).load(imageUri).into(imageView)
        }

        // return anything that our app didn't handle, This preserves the default platform behaviour for text and anything else that
        // we're not implementing custom handling for.
        return remaining
    }

}