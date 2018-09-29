package com.genaku.maskededittext.maskCharacters

internal class LiteralCharacter(private var character: Char? = null) : MaskCharacter() {

    override val isPrepopulate: Boolean
        get() = character != null

    override fun isValidCharacter(ch: Char): Boolean = character == null || character == ch

    override fun processCharacter(ch: Char): Char = character ?: ch

}
