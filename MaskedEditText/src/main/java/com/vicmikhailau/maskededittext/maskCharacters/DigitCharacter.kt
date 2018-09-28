package com.vicmikhailau.maskededittext.maskCharacters

internal class DigitCharacter : MaskCharacter() {
    override fun isValidCharacter(ch: Char): Boolean = Character.isDigit(ch)
}
