package com.example.guessme.api


import com.example.guessme.data.User
import org.json.JSONObject

class Json() {
    var jsonObject = JSONObject()

    fun login(nickname : String, pw : String) : String{
        jsonObject.put("nickname",nickname)
        jsonObject.put("password",pw)
        return jsonObject.toString()
    }

    fun signup(nickname: String, pw : String) : String{
        jsonObject.put("nickname", nickname)
        jsonObject.put("password",pw)
        return jsonObject.toString()
    }

    fun isJson(str : String):Boolean{
        str.trim()
        if(str[0] == '{' || str[0] == '[')
            return true
        return false
    }
    fun isnull(str : String):Boolean{
        str.trim()
        if(str[2] == 's' || str[3] == 'u')
            return false
        return true
    }
}