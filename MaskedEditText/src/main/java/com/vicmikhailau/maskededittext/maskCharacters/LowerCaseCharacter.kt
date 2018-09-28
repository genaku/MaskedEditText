package com.vicmikhailau.maskededittext.maskCharacters

internal class LowerCaseCharacter : MaskCharacter() {

    override fun isValidCharacter(ch: Char): Boolean = Character.isLowerCase(ch)

    override fun processCharacter(ch: Char): Char = Character.toLowerCase(ch)

}
