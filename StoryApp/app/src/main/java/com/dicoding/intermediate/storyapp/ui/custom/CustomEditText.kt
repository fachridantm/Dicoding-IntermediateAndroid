package com.dicoding.intermediate.storyapp.ui.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import androidx.core.content.ContextCompat
import com.dicoding.intermediate.storyapp.R
import com.google.android.material.textfield.TextInputEditText

class CustomEditText : TextInputEditText {

    private var errorBackground: Drawable? = null
    private var defaultBackground: Drawable? = null
    private var isError: Boolean = false

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

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        background = if (isError) {
            errorBackground
        } else {
            defaultBackground
        }
    }

    private fun init() {
        errorBackground = ContextCompat.getDrawable(context, R.drawable.bg_edt_error)
        defaultBackground = ContextCompat.getDrawable(context, R.drawable.bg_edt_default)

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val input = p0.toString()
                when (inputType) {
                    EMAIL -> {
                        if (!Patterns.EMAIL_ADDRESS.matcher(input).matches()) {
                            error = context.getString(R.string.email_validation)
                            isError = true
                        } else {
                            isError = false
                        }
                    }
                    PASSWORD -> {
                        isError = if (input.length < 6) {
                            setError(context.getString(R.string.password_length), null)
                            true
                        } else {
                            false
                        }
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                val input = p0.toString()
                when (inputType) {
                    EMAIL -> {
                        if (!Patterns.EMAIL_ADDRESS.matcher(input).matches()) {
                            error = context.getString(R.string.email_validation)
                            isError = true
                        } else {
                            isError = false
                        }
                    }
                    PASSWORD -> {
                        isError = if (input.length < 6) {
                            setError(context.getString(R.string.password_length), null)
                            true
                        } else {
                            false
                        }
                    }
                }
            }
        })
    }

    companion object {
        const val EMAIL = 0x00000021
        const val PASSWORD = 0x00000081
    }
}

