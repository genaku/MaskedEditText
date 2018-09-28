package com.vicmikhailau.maskededittext

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

import com.vicmikhailau.maskededittext.formattedString.IFormattedString

import java.lang.ref.WeakReference

class MaskedWatcher(
        maskedFormatter: MaskedFormatter,
        editText: EditText,
        private val noMask: (text: String) -> Boolean = { false }
) : TextWatcher {

    private val mMaskFormatter: WeakReference<MaskedFormatter> = WeakReference(maskedFormatter)
    private val mEditText: WeakReference<EditText> = WeakReference(editText)
    private var oldFormattedValue = ""
    private var oldCursorPosition: Int = 0

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        this.oldCursorPosition = mEditText.get()?.selectionStart ?: 0
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable?) {
        val value = s?.toString() ?: return

        oldFormattedValue = if (noMask(value)) {
            setRawText(value)
            value
        } else {
            val formattedString = getFormattedString(value)
            setFormattedText(formattedString)
            formattedString.toString()
        }
    }

    private fun getFormattedString(value: String): IFormattedString =
            mMaskFormatter.get()!!.formatString(limitTextByLength(value))

    private fun limitTextByLength(value: String): String =
            if (value.length > oldFormattedValue.length && mMaskFormatter.get()!!.maskLength < value.length)
                oldFormattedValue
            else
                value

    private fun setFormattedText(formattedString: IFormattedString) {
        val editText = mEditText.get() ?: return

        editText.removeTextChangedListener(this)
        editText.setText(formattedString)
        editText.addTextChangedListener(this)

        var newCursorPosition = oldCursorPosition

        val deltaLength = formattedString.length - oldFormattedValue.length
        when {
            deltaLength > 0 -> newCursorPosition += deltaLength
            deltaLength < 0 -> newCursorPosition -= 1
            else -> {
                val mask = mMaskFormatter.get()?.mMask
                newCursorPosition = Math.max(1, Math.min(newCursorPosition, mMaskFormatter.get()!!.maskLength))
                if (mask!![newCursorPosition - 1].isPrepopulate)
                    newCursorPosition -= 1
            }
        }
        newCursorPosition = Math.max(0, Math.min(newCursorPosition, formattedString.length))
        editText.setSelection(newCursorPosition)
    }

    private fun setRawText(value: String) {
        val editText = mEditText.get() ?: return

        editText.removeTextChangedListener(this)
        editText.setText(value)
        editText.addTextChangedListener(this)

        var newCursorPosition = oldCursorPosition

        val deltaLength = value.length - oldFormattedValue.length
        when {
            deltaLength > 0 -> newCursorPosition += deltaLength
            deltaLength < 0 -> newCursorPosition -= 1
        }
        newCursorPosition = Math.max(0, Math.min(newCursorPosition, value.length))
        editText.setSelection(newCursorPosition)
    }

}
