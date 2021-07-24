package com.submission.githubuserapi.uicustom

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.res.ResourcesCompat
import com.submission.githubuserapi.R

class EditText : AppCompatEditText, View.OnTouchListener {

    private lateinit var mClearButtonImage: Drawable

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        mClearButtonImage = ResourcesCompat.getDrawable(
            resources,
            R.drawable.baseline_cancel_24,
            null
        ) as Drawable
        setOnTouchListener(this)

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
                if(p0.toString().isNotEmpty()) showClearButton() else hideClearButton()
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
       if (compoundDrawables[2] != null) {
           val clearButtonStart: Float
           val clearButtonEnd: Float
           var isClearButtonClicked = false
           when(layoutDirection) {
               View.LAYOUT_DIRECTION_RTL -> {
                   clearButtonEnd = (mClearButtonImage.intrinsicWidth + paddingStart).toFloat()
                   when {
                       event.x < clearButtonEnd -> isClearButtonClicked = true
                   }
               }
               else -> {
                   clearButtonStart = (width - paddingEnd - mClearButtonImage.intrinsicWidth).toFloat()
                   when {
                       event.x > clearButtonStart -> isClearButtonClicked = true
                   }
               }
           }
           when {
               isClearButtonClicked -> when (event.action) {
                   MotionEvent.ACTION_DOWN -> {
                       mClearButtonImage = ResourcesCompat.getDrawable(resources, R.drawable.baseline_cancel_24, null) as Drawable
                       showClearButton()
                       return true
                   }
                   MotionEvent.ACTION_UP -> {
                       mClearButtonImage = ResourcesCompat.getDrawable(resources, R.drawable.baseline_cancel_24, null) as Drawable
                       when {
                           text != null -> {
                               text?.clear()
                               val inputManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                               inputManager.hideSoftInputFromWindow(this.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
                           }

                       }
                       hideClearButton()
                       return true
                   }
                   else -> return false
               }
               else -> return false
           }
       }
        return false
    }

    private fun showClearButton() {
        setCompoundDrawablesWithIntrinsicBounds(null, null,
            mClearButtonImage, null)
    }
    private fun hideClearButton() {
        setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
    }
}