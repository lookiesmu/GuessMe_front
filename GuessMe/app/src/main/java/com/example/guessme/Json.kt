package com.example.guessme


import org.json.JSONObject

class Json {
    var jsonObject = JSONObject()

    fun login(nickname : String, pw : String) : String{
        jsonObject.put("id",nickname)
        jsonObject.put("pw",pw)
        return jsonObject.toString()
    }

    fun signup(nickname : String?, pw : String) : String{
        jsonObject.put("id", nickname)
        jsonObject.put("pw",pw)
        return jsonObject.toString()
    }
}