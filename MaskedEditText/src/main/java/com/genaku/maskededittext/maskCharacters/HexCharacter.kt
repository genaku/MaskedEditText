package com.genaku.maskededittext.maskCharacters

internal class HexCharacter : MaskCharacter() {

    override fun isValidCharacter(ch: Char): Boolean = ch.toUpperCase() in HEX_CHARS

    override fun processCharacter(ch: Char): Char = Character.toUpperCase(ch)

    companion object {
        private const val HEX_CHARS = "0123456789ABCDEF"
    }

}
