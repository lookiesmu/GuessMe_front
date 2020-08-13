package com.example.guessme

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.guessme.api.Json
import com.example.guessme.api.Okhttp
import com.example.guessme.data.Quiz
import kotlinx.android.synthetic.main.activity_solve_quiz.*
import kotlinx.android.synthetic.main.dialog_score.*
import org.json.JSONObject

class SolveQuizActivity : AppCompatActivity() {


    val solve_quiz_list: ArrayList<Quiz> = arrayListOf() //퀴즈 리스트 담을 배열 생성
    val my_answer_list: ArrayList<Int> = arrayListOf(-1,-1,-1,-1,-1) //퀴즈에 대한 유저의 정답 리스트(초기값 : -1)



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

            rv_solve_quiz.adapter = SolveQuizAdapter(this@SolveQuizActivity, solve_quiz_list, my_answer_list)

        }

    }

    fun ComputeGrade():Int{

        var grade = 0

        for (i in 0 until solve_quiz_list.size){
            if (my_answer_list[i] == solve_quiz_list.get(i).answer)
                grade += 20
        }

        return  grade
    }

    fun SolveQuiz_Click_Listener(view : View){
        when(view.id){
            R.id.btn_solve_quiz ->{
                Log.d("마이앤썰리스트굿굿",my_answer_list.toString())
                if (-1 in my_answer_list)
                    Toast.makeText(applicationContext, "퀴즈 항목을 모두 입력하세요.", Toast.LENGTH_SHORT).show()
                else{
                    val mark = ComputeGrade()
                    Log.d("마이그레이드굿굿",mark.toString())

                    // mark를 dialog에 띄우기
                    val text = mark.toString()+"점"
                    val dlg = ScoreDialog(this)
//                    dlg.setOnOKClickedListener{
//                    }
                    dlg.start(text)

                    // mark를 서버에 POST username, score 점수 반환
                    // user 이름이 이미 랭크에 있을 경우 Toast 로 이미 제출한 퀴즈입니다 띄우기
                }
            }
        }
    }

    override fun onPause() {
        asynctask().cancel(true)
        super.onPause()
    }

    override fun onBackPressed() {
        Log.d("finish","finish")
        this@SolveQuizActivity.finish()
    }


}