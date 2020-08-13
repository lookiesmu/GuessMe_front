package com.example.guessme.data

import java.io.Serializable

data class SolveAnswer (
    var myanswer: Int //내가 입력한 퀴즈 답
) : Serializable {
    constructor():this(0)
}
