package com.vicmikhailau.maskededittext

import android.content.Context
import android.content.res.TypedArray
import android.support.v7.widget.AppCompatEditText
import android.util.AttributeSet

import com.vicmikhailau.maskededittext.formattedString.IFormattedString

class MaskedEditText(context: Context, attrs: AttributeSet) : AppCompatEditText(context, attrs) {

    private var mMaskedFormatter: MaskedFormatter? = null
    private var mMaskedWatcher: MaskedWatcher? = null

    val maskString: String?
        get() = mMaskedFormatter?.maskString

    val unMaskedText: String
        get() {
            val currentText = text?.toString() ?: ""
            val formattedString = mMaskedFormatter!!.formatString(currentText)
            return formattedString.unMaskedString
        }

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.MaskedEditText)

        if (typedArray.hasValue(R.styleable.MaskedEditText_mask)) {
            val maskStr = typedArray.getString(R.styleable.MaskedEditText_mask)

            if (!maskStr.isNullOrEmpty()) {
                setMask(maskStr!!)
            }
        }

        typedArray.recycle()
    }

    fun setMask(mMaskStr: String) {
        mMaskedFormatter = MaskedFormatter(mMaskStr)

        if (mMaskedWatcher != null) {
            removeTextChangedListener(mMaskedWatcher)
        }

        mMaskedWatcher = MaskedWatcher(mMaskedFormatter!!, this)
        addTextChangedListener(mMaskedWatcher)
    }

}
