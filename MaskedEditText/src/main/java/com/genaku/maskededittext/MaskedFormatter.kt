package com.genaku.maskededittext

import com.genaku.maskededittext.formattedString.IFormattedString

class MaskedFormatter internal constructor() {

    var mMask: Mask? = null

    val maskString: String?
        get() = mMask?.formatString

    val maskLength: Int
        get() = mMask?.size() ?: 0

    constructor(fmtString: String) : this() {
        this.setMask(fmtString)
    }

    fun setMask(fmtString: String) {
        mMask = Mask(fmtString)
    }

    fun formatString(value: String): IFormattedString {
        return mMask!!.getFormattedString(value)
    }

}
