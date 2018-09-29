package com.genaku.maskededittext.maskCharacters

internal class UpperCaseCharacter : MaskCharacter() {

    override fun isValidCharacter(ch: Char): Boolean = Character.isUpperCase(ch)

    override fun processCharacter(ch: Char): Char = Character.toUpperCase(ch)

}
