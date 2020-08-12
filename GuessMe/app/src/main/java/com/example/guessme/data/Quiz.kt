package com.example.guessme.data

import java.io.Serializable

data class Quiz (
    var quizid: Int, //퀴즈 고유 ID
    var content: String, //퀴즈 내용
    var answer: Int //퀴즈 답
) : Serializable{
    constructor():this(0,"",0)
    constructor(content: String):this(0,content,0)
}
