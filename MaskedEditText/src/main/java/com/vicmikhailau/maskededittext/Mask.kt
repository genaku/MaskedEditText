package com.vicmikhailau.maskededittext

import com.vicmikhailau.maskededittext.formattedString.FormattedString
import com.vicmikhailau.maskededittext.formattedString.IFormattedString
import com.vicmikhailau.maskededittext.maskCharacters.MaskCharacter

import java.util.ArrayList

class Mask() {

    var formatString: String = ""
    private var mMask: List<MaskCharacter> = emptyList()
    private val mFabric: MaskCharacterFabric = MaskCharacterFabric()
    private var mPrepopulateCharacter: MutableList<MaskCharacter> = ArrayList()

    constructor(fmtString: String) : this() {
        formatString = fmtString
        mMask = buildMask(formatString)
    }

    fun size(): Int = mMask.size

    operator fun get(index: Int): MaskCharacter = mMask[index]

    fun isValidPrepopulateCharacter(ch: Char, at: Int): Boolean =
            try {
                val character = mMask[at]
                character.isPrepopulate && character.isValidCharacter(ch)
            } catch (e: IndexOutOfBoundsException) {
                false
            }

    fun isValidPrepopulateCharacter(ch: Char): Boolean =
            mPrepopulateCharacter.any { it.isValidCharacter(ch) }

    private fun buildMask(fmtString: String): List<MaskCharacter> {
        val result = ArrayList<MaskCharacter>()
        mPrepopulateCharacter = ArrayList()
        for (ch in fmtString.toCharArray()) {
            val maskCharacter = mFabric.buildCharacter(ch)
            if (maskCharacter.isPrepopulate) {
                mPrepopulateCharacter.add(maskCharacter)
            }
            result.add(maskCharacter)
        }
        return result
    }

    fun getFormattedString(value: String): IFormattedString = FormattedString(this, value)

}
