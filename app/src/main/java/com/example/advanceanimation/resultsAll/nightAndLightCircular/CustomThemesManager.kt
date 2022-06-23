package com.example.advanceanimation.resultsAll.nightAndLightCircular

import androidx.annotation.ColorRes

/*
Here we create a place for managing app theme, ThemeManager and object which describes the Theme itself
Theme will contain different sub-themes for each design component
For text view and button we create separate themes

And theme itself will be enum containing different combination mapped over some finite number of themes

And our ThemeManager will just provide single instance of current theme
 */

object ThemeManager {

    /*
    Instead of explicitly setting theme to each view it would be better if views subscribed to theme changes and reactively
    updated by themselves, For this we add ability for theme listener
     */
    private val listeners = mutableSetOf<ThemeChangedListener>()
    var theme = Theme.LIGHT
        set(value) {
            field = value
            listeners.forEach { listener -> listener.onThemeChanged(value) }
        }

    interface ThemeChangedListener {
        fun onThemeChanged(theme: Theme)
    }

    fun addListener(listener: ThemeChangedListener) {
        listeners.add(listener)
    }

    fun removeListener(listener: ThemeChangedListener) {
        listeners.remove(listener)
    }

    data class TextViewTheme(@ColorRes val textColor: Int)
    data class ViewGroupTheme(@ColorRes val backgroundColor: Int)
    data class ButtonTheme(@ColorRes val backgroundTint: Int, @ColorRes val textColor: Int)

    enum class Theme(
        val buttonTheme: ButtonTheme,
        val textViewTheme: TextViewTheme,
        val viewGroupTheme: ViewGroupTheme
    ) {
        DARK(
            buttonTheme = ButtonTheme(
                backgroundTint = android.R.color.holo_green_dark,
                textColor = android.R.color.white
            ),
            textViewTheme = TextViewTheme(
                textColor = android.R.color.white
            ),
            viewGroupTheme = ViewGroupTheme(
                backgroundColor = android.R.color.background_dark
            )
        ),
        LIGHT(
            buttonTheme = ButtonTheme(
                backgroundTint = android.R.color.holo_green_light,
                textColor = android.R.color.black
            ),
            textViewTheme = TextViewTheme(
                textColor = android.R.color.black
            ),
            viewGroupTheme = ViewGroupTheme(
                backgroundColor = android.R.color.background_light
            )
        ),
    }

}
