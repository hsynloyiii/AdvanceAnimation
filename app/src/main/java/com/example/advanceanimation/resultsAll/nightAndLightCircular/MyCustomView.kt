package com.example.advanceanimation.resultsAll.nightAndLightCircular

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.example.advanceanimation.R

/*
In order to not change our layout we can use custom layout inflater factories\
The idea is that we can intercept inflating views based on the names (“TextView”, “Button”, “com.MyTextView” etc.).
And we can provide our own implementations for base views
And we should set this before onCreate in MainActivity
 */
class MyLayoutInflater(
    private val delegate: AppCompatDelegate
) : LayoutInflater.Factory2 {
    override fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? {
        return when (name) {
            "TextView" -> MyTextView(context, attrs)
            "LinearLayout" -> MyLinearLayout(context, attrs)
            "Button" -> MyButton(context, attrs, R.attr.buttonStyle)
            else -> delegate.createView(parent, name, context, attrs)
        }
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return onCreateView(null, name, context, attrs)
    }
}

/*
Next we need to create our own wrapper for views to support dynamic theme changing
Using the listener we made in ThemeManager we update our view and trigger them to update on theme changed
 */
class MyTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr), ThemeManager.ThemeChangedListener {

//    fun setTheme(theme: ThemeManager.Theme) {
//        setTextColor(
//            ContextCompat.getColor(
//                context,
//                theme.textViewTheme.textColor
//            )
//        )
//    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        ThemeManager.addListener(this)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        ThemeManager.addListener(this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        ThemeManager.removeListener(this)
    }

    // Instead of setTheme we now have this overrided method
    override fun onThemeChanged(theme: ThemeManager.Theme) {
        setTextColor(
            ContextCompat.getColor(
                context,
                theme.textViewTheme.textColor
            )
        )
    }
}


// ---------------------------------------------------------------

class MyLinearLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), ThemeManager.ThemeChangedListener {

    override fun onFinishInflate() {
        super.onFinishInflate()
        ThemeManager.addListener(this)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        ThemeManager.addListener(this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        ThemeManager.removeListener(this)
    }

    // Instead of setTheme we now have this overrided method
    override fun onThemeChanged(theme: ThemeManager.Theme) {
        setBackgroundColor(
            ContextCompat.getColor(
                context,
                theme.viewGroupTheme.backgroundColor
            )
        )
    }
}


// -------------------------------------------------------------

class MyButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatButton(context, attrs, defStyleAttr), ThemeManager.ThemeChangedListener {

    override fun onFinishInflate() {
        super.onFinishInflate()
        ThemeManager.addListener(this)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        ThemeManager.addListener(this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        ThemeManager.removeListener(this)
    }

    // Instead of setTheme we now have this overrided method
    override fun onThemeChanged(theme: ThemeManager.Theme) {
        setTextColor(
            ContextCompat.getColor(
                context,
                theme.buttonTheme.textColor
            )
        )
        backgroundTintList = ColorStateList.valueOf(
            ContextCompat.getColor(
                context,
                theme.buttonTheme.backgroundTint
            )
        )
    }
}



