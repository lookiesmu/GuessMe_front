package com.example.guessme.data

import java.io.Serializable

data class User (
    var nickname: String?
) : Serializable{
    constructor() : this("")
}