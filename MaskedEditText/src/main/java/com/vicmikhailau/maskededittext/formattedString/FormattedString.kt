package com.vicmikhailau.maskededittext.formattedString

import com.vicmikhailau.maskededittext.Mask

internal class FormattedString(mask: Mask, rawString: String) : AbstractFormattedString(mask, rawString) {

    override fun buildRawString(str: String): String {
        val builder = StringBuilder()
        val inputLen = Math.min(mMask.size(), str.length)
        for (i in 0 until inputLen) {
            val ch = str[i]
            if (!mMask.isValidPrepopulateCharacter(ch, i))
                builder.append(ch)
        }
        return builder.toString()
    }

    override fun formatString(): String {
        val builder = StringBuilder()

        var strIndex = 0
        var maskCharIndex = 0
        var stringCharacter: Char

        while (strIndex < inputString.length && maskCharIndex < mMask.size()) {
            val maskChar = mMask.get(maskCharIndex)

            stringCharacter = inputString[strIndex]

            if (maskChar.isValidCharacter(stringCharacter)) {
                builder.append(maskChar.processCharacter(stringCharacter))
                strIndex += 1
                maskCharIndex += 1
            } else if (maskChar.isPrepopulate) {
                builder.append(maskChar.processCharacter(stringCharacter))
                maskCharIndex += 1
            } else {
                strIndex += 1
            }
        }

        return builder.toString()
    }

}
