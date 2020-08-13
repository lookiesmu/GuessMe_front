package com.example.guessme

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.guessme.api.Json
import com.example.guessme.api.Okhttp
import com.example.guessme.data.Quiz
import com.example.guessme.data.SolveAnswer
import kotlinx.android.synthetic.main.activity_create_quiz.*
import kotlinx.android.synthetic.main.activity_search_quiz.*
import kotlinx.android.synthetic.main.activity_solve_quiz.*
import org.json.JSONObject
import java.lang.Exception
import java.util.logging.Logger.global

class SolveQuizActivity : AppCompatActivity() {


    val solve_quiz_list: ArrayList<Quiz> = arrayListOf() //퀴즈 리스트 담을 배열 생성
    val my_answer_list: ArrayList<SolveAnswer> = arrayListOf() //퀴즈에 대한 유저의 정답 리스트

    lateinit var solve_adapter : SolveQuizAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_solve_quiz)

        val nickname = intent.getStringExtra("nickname")
        tv_solve_quiz.setText(String.format("%s님의 퀴즈를 풀어보세요!",nickname))
        Solve_Control().GET_QUIZ(nickname)
    }

    inner class Solve_Control(){

        //닉네임에 해당하는 퀴즈 바로 받아오기
        fun GET_QUIZ(nickname : String) {
            val url = getString(R.string.server_url) + "/quizzes/" + nickname
            asynctask().execute(url)
        }

        fun ComputeGrade():Int{

            var grade = 0

            for (i in 0 until solve_quiz_list.size){
                if (my_answer_list.get(i).myanswer == solve_quiz_list.get(i).answer)
                    grade += 20
            }

            return  grade
        }

    }

    inner class asynctask : AsyncTask<String, Void, String>(){


        override fun doInBackground(vararg params: String): String {
            val url = params[0]
            return Okhttp(applicationContext).GET(url)
        }


        override fun onPostExecute(response: String?) {
            //넘어온 값이 없을 때 로그 찍고 리턴
            if(response.isNullOrEmpty()) {
                Toast.makeText(applicationContext,"Search_activity", Toast.LENGTH_SHORT).show()
                Log.d("Solve_Activity", "null in")
                return
            }
            Log.d("Solve_Activity",response)
            if(!Json().isJson(response)){
                Log.d("퀴즈 입력 통신 에러", response)
                Toast.makeText(applicationContext,"네트워크 통신 오류",Toast.LENGTH_SHORT).show()
                return
            }

            val jsonObj = JSONObject(response) // 객체 전체 응답 받아오기
            val jsonObj_embedded = jsonObj.getJSONObject("_embedded") //특정 객체 받아오기
            val jsonQuizAry = jsonObj_embedded.getJSONArray("quizList") //특정 배열 받아오기

            for (i in 0 until jsonQuizAry.length()) {
                val json_ojt: JSONObject = jsonQuizAry.getJSONObject(i)
                solve_quiz_list.add(Quiz(json_ojt.getInt("quizId"),json_ojt.getString("content"), json_ojt.getInt("answer")))
            }

            rv_solve_quiz.adapter = SolveQuizAdapter(this@SolveQuizActivity, solve_quiz_list)

        }

    }

    fun SolveQuiz_Click_Listener(view : View){
        when(view.id){
        }
    }

    override fun onPause() {
        asynctask().cancel(true)
        super.onPause()
    }
}