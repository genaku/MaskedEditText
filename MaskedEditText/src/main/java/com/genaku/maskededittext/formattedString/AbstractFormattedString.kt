package com.genaku.maskededittext.formattedString

import com.genaku.maskededittext.Mask

abstract class AbstractFormattedString(val mMask: Mask, val inputString: String) : IFormattedString {

    private val mFormattedString: String by lazy { formatString() }

    override val unMaskedString: String by lazy { buildRawString(inputString) }

    abstract fun formatString(): String
    abstract fun buildRawString(str: String): String

    override val length: Int
        get() = toString().length

    override fun toString(): String = mFormattedString

    override fun subSequence(startIndex: Int, endIndex: Int): CharSequence =
            toString().subSequence(startIndex, endIndex)

    override fun get(index: Int): Char = toString()[index]

}
