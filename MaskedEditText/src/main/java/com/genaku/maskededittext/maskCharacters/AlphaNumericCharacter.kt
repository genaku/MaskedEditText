package com.genaku.maskededittext.maskCharacters

internal class AlphaNumericCharacter : MaskCharacter() {
    override fun isValidCharacter(ch: Char): Boolean = Character.isLetterOrDigit(ch)
}
