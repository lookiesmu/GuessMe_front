package com.example.guessme.api


import com.example.guessme.data.Quiz
import com.example.guessme.data.QuizList
import com.example.guessme.data.User
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

    fun isJson(str : String):Boolean{
        str.trim()
        if(str[0] == '{' || str[0] == '[')
            return true
        return false
    }

    fun createQuiz(quizList: String): String{
        // string -> QuizList
            // 요 부분 일단 보류
        // 제이슨 리스트로 저장
            // 요 방식으로 잘 되는지 확인할 것!
        var jsonArr = JSONArray(quizList)
        return jsonArr.toString()
    }

    fun deleteQuiz(): String {
        // 삭제할 때 뭐 보내는지 물어보기!
        return ""
    }


}