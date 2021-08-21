package org.cerion.symcalcapp

enum class Key {
    DEL,
    EVAL,
    NUM_0,
    NUM_1,
    NUM_2,
    NUM_3,
    NUM_4,
    NUM_5,
    NUM_6,
    NUM_7,
    NUM_8,
    NUM_9,
    DOT,
    PLUS,
    MINUS,
    TIMES,
    DIVIDE;

    fun inputValue(): String {
        if (ordinal >= NUM_0.ordinal && ordinal <= NUM_9.ordinal)
            return (ordinal - NUM_0.ordinal).toString()

        return when(this) {
            DOT -> "."
            PLUS -> "+"
            DIVIDE -> "/"
            TIMES -> "*"
            MINUS -> "-"
            else -> throw UnsupportedOperationException()
        }
    }
}

