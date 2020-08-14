package com.example.guessme.api

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.example.guessme.data.User

class User_Control(context: Context) {
    val sharedPreferences = context.getSharedPreferences("User_Info", MODE_PRIVATE)
    val editPreferences = sharedPreferences.edit()

    fun set_token(token: String) {
        editPreferences.putString("token", token).apply()
    }

    fun set_user(user: User) {
        editPreferences.putString("nickname", user.nickname)
        editPreferences.apply()
    }

    fun get_token(): String? {
        return sharedPreferences.getString("token", null)
    }

    fun get_user(): User {
        var user = User(
            sharedPreferences.getString("nickname", "")
        )
        return user
    }
}