package com.vicmikhailau.maskededittext.maskCharacters

internal class LetterCharacter : MaskCharacter() {
    override fun isValidCharacter(ch: Char): Boolean = Character.isLetter(ch)
}
