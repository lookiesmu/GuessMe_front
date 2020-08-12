package com.example.guessme.data

import java.io.Serializable

data class Quiz (
    val quizid: Int,
    var content: String,
    var answer: Int
) : Serializable
