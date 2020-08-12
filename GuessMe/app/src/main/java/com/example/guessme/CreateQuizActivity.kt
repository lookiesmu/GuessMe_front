package com.example.guessme

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.guessme.api.Json
import com.example.guessme.api.Okhttp
import com.example.guessme.data.Quiz
import com.example.guessme.util.Constants.Companion.BASE_URL
import org.json.JSONObject

class CreateQuizActivity : AppCompatActivity() {

    val quiz_url = BASE_URL + "/quizzes"
    val createQuizList: ArrayList<Quiz> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_quiz)


    }

    inner class CreateQuiz_Control {
        // 서버로부터 퀴즈 문항 get
        fun GET_CreateQuiz(){
            asynctask().execute("0", quiz_url)

        }

        // 퀴즈 생성
        fun POST_CreateQuiz(){
            asynctask().execute("1", quiz_url, createQuizList.toString())
        }


    }

    inner class asynctask : AsyncTask<String, Void, String>() {
        var state: Int = -1 //state == 0 : GET_퀴즈조, state == 1 : POST_퀴즈생
        override fun onPreExecute() {
            // 사전
        }

        override fun doInBackground(vararg params: String): String {
            state = Integer.parseInt(params[0])
            val url = params[1]
            var response: String = ""
            when (state) {
                0 -> response = Okhttp().GET(url)
                1 -> {
                    val quizzes = params[2]
                    response = Okhttp().POST(url, Json()
                        .createQuiz(quizzes))
                }
            }
            return response
        }

        override fun onPostExecute(response: String) {
            if (response.isEmpty()) {
                Log.d("CreateQuiz_Activity", "null")
                return
            }
            if (!Json().isJson(response)) {
                Toast.makeText(applicationContext, "네트워크 통신 오류", Toast.LENGTH_SHORT).show()
                Log.d("CreateQuiz_Activity", response)
                return
            }

            val jsonObj = JSONObject(response)
            when (state) {
                0 -> {
                    if (jsonObj.getBoolean("success")) {
                        // 리사이클러뷰에 띄우기
                    } else
                        Toast.makeText(applicationContext, "사용불가능한 닉네임입니다", Toast.LENGTH_SHORT)
                            .show()
                }
                1 -> {
                    if (jsonObj.getBoolean("success")) {
                        SignUp_Control().Dialog_Signup()
                    } else {
                        Toast.makeText(applicationContext, "회원가입 실패", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            loadingDialog.dismiss()
        }
    }


}