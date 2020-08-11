package com.example.guessme


import org.json.JSONObject

class Json() {
    var jsonObject = JSONObject()

    fun login(nickname : String, pw : String) : String{
        jsonObject.put("nickname",nickname)
        jsonObject.put("password",pw)
        return jsonObject.toString()
    }

    fun signup(user : User?, pw : String) : String{
        jsonObject.put("ninkname", user!!.nickname)
        jsonObject.put("password",pw)
        return jsonObject.toString()
    }
    fun isJson(str : String):Boolean{
        str.trim()
        if(str[0] == '{' || str[0] == '[')
            return true
        return false
    }
}