package com.vicmikhailau.maskededittext

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

import com.vicmikhailau.maskededittext.formattedString.IFormattedString

import java.lang.ref.WeakReference

class MaskedWatcher(maskedFormatter: MaskedFormatter, editText: EditText) : TextWatcher {

    private val mMaskFormatter: WeakReference<MaskedFormatter> = WeakReference(maskedFormatter)
    private val mEditText: WeakReference<EditText> = WeakReference(editText)
    private var oldFormattedValue = ""
    private var oldCursorPosition: Int = 0

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        this.oldCursorPosition = mEditText.get()?.selectionStart ?: 0
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable?) {
        s ?: return

        var value = s.toString()

        if (value.length > oldFormattedValue.length && mMaskFormatter.get()!!.maskLength < value.length) {
            value = oldFormattedValue
        }

        val formattedString = mMaskFormatter.get()!!.formatString(value)

        setFormattedText(formattedString)
        oldFormattedValue = formattedString.toString()
    }

    private fun setFormattedText(formattedString: IFormattedString) {
        val editText = mEditText.get() ?: return
        val deltaLength = formattedString.length - oldFormattedValue.length

        editText.removeTextChangedListener(this)
        editText.setText(formattedString)
        editText.addTextChangedListener(this)

        var newCursorPosition = oldCursorPosition

        if (deltaLength > 0) {
            newCursorPosition += deltaLength
        } else if (deltaLength < 0) {
            newCursorPosition -= 1
        } else {
            val mask = mMaskFormatter.get()?.mMask
            newCursorPosition = Math.max(1, Math.min(newCursorPosition, mMaskFormatter.get()!!.maskLength))
            if (mask!![newCursorPosition - 1].isPrepopulate)
                newCursorPosition -= 1
        }
        newCursorPosition = Math.max(0, Math.min(newCursorPosition, formattedString.length))
        editText.setSelection(newCursorPosition)
    }

}
