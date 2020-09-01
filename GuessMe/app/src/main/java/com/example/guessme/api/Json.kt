package com.example.guessme.api


import android.util.Log
import org.json.JSONArray
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

    fun submitScore(score: String): String{
        jsonObject.put("score",score)
        return  jsonObject.toString()
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
    fun createQuiz(quizList: String): String{
        var jsonArr = JSONArray(quizList)
        //Log.d("network",jsonArr.toString())

        return jsonArr.toString()
    }
}