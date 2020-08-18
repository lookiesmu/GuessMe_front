package com.example.guessme

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.guessme.api.Okhttp
import com.example.guessme.api.User_Control

class InitActivity : AppCompatActivity() {
//    val user_token = Okhttp(applicationContext).token

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_init)

//        Log.d("InitActivityaaaa",User_Control(applicationContext).get_token())
        if (!User_Control(applicationContext).get_token().isNullOrEmpty()) {  // 토큰이 살아있다면 -> 메인화면
            val intent = Intent(applicationContext,SearchQuizActivity::class.java)
            startActivity(intent)
            finish()
        } else{     // 토큰이 없으니 로그인하러
            val intent = Intent(applicationContext,SignInActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}