package com.vicmikhailau.maskededittextsample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.widget.EditText

import com.vicmikhailau.maskededittext.MaskedEditText
import com.vicmikhailau.maskededittext.MaskedFormatter
import com.vicmikhailau.maskededittext.MaskedWatcher

class MainActivity : AppCompatActivity() {

    /**
     * Use specific values for create your own mask (see example below or in xml):
     * ANYTHING_KEY = '*'
     * DIGIT_KEY = '#'
     * UPPERCASE_KEY = 'U';
     * LOWERCASE_KEY = 'L';
     * ALPHA_NUMERIC_KEY = 'A';
     * LITERAL_KEY = '\'';
     * CHARACTER_KEY = '?';
     * HEX_KEY = 'H';
     */

    /**
     * For getting unmasked text for MaskedEditText mEdtMaskedCustom just use mEdtMaskedCustom.getUnMaskedString().
     * For getting unmasked text for default EditText just use formatter.formatString(text).getUnMaskedString().
     */

    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    private lateinit var mEdtMaskedCustom: MaskedEditText
    private lateinit var mEdtMasked: EditText
    private var formatter: MaskedFormatter? = null

    // ===========================================================
    // Constructors
    // ===========================================================

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass
    // ===========================================================


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViews()
        setMask("+7(###) ###-##-##")
    }

    // ===========================================================
    // Listeners, methods for/from Interfaces
    // ===========================================================

    // ===========================================================
    // Methods
    // ===========================================================

    private fun findViews() {
        mEdtMaskedCustom = findViewById(R.id.edt_masked_custom)
        mEdtMasked = findViewById(R.id.edt_masked)
    }

    /**
     * You cas use MaskedEditText declared in xml with attribute named mask
     * or
     * set mask in code for default EdiText
     *
     * @param mask your mask
     */
    private fun setMask(mask: String) {
        val formatter = MaskedFormatter(mask)
        mEdtMasked.addTextChangedListener(InputTypeSwitcher(mEdtMasked, ::noMask))
        mEdtMasked.addTextChangedListener(MaskedWatcher(formatter, mEdtMasked, ::noMask))
        val s = formatter.formatString(mEdtMasked.text.toString()).unMaskedString
    }

    private class InputTypeSwitcher(
            private val editText: EditText,
            private val noMask: (text: String) -> Boolean
    ) : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            val value = s.toString()
            if (noMask(value)) {
                editText.inputType = InputType.TYPE_NULL
            } else {
                editText.inputType = InputType.TYPE_CLASS_PHONE
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

    }

    private fun noMask(value: String): Boolean = value.isEmpty() || value[0] !in "+(0123456789"


    fun getUnMaskedTextForEdtCustom() {
        mEdtMaskedCustom.unMaskedText
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================

}
