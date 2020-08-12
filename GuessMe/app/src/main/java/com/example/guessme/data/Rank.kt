package com.example.guessme.data

import java.io.Serializable

data class Rank (
    var index : Int,
    val nickname : String,
    var score : Int
) : Serializable
