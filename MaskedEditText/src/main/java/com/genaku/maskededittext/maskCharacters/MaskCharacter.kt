package com.genaku.maskededittext.maskCharacters

abstract class MaskCharacter {

    open val isPrepopulate: Boolean
        get() = false

    abstract fun isValidCharacter(ch: Char): Boolean

    open fun processCharacter(ch: Char): Char = ch
}


