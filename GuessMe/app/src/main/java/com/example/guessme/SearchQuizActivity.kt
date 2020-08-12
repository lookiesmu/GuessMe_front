package com.example.guessme

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.guessme.api.Json
import com.example.guessme.api.Okhttp
import com.example.guessme.data.Quiz
import com.example.guessme.data.User
import kotlinx.android.synthetic.main.activity_search_quiz.*
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_solve_quiz.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.Serializable

class SearchQuizActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("로그1","로그로그")
        setContentView(R.layout.activity_search_quiz)
        Log.d("로그2","로그로그")
        SearchQuiz_Control().edit_init()
    }


    inner class SearchQuiz_Control() {
        fun edit_init(){
            sq_et_nickname.addTextChangedListener(EditListener())
        }

        fun edit_check() : Boolean{

            if (sq_et_nickname.text.isNullOrEmpty()) {
                Toast.makeText(applicationContext, "닉네임을 입력해주세요.", Toast.LENGTH_LONG).show()
                return false
            }
            return true

        }

        fun GET_QUIZ(nickname : String){
            val url = getString(R.string.server_url) + "/quizzes/" + nickname
            asynctask().execute(url)
        }
    }

    inner class asynctask : AsyncTask<String, Void, String>(){
        override fun doInBackground(vararg params: String): String {
            val url = params[0]
            Log.d("두인백그라운드",url)
            return Okhttp(applicationContext).GET(url)
        }
        override fun onPostExecute(response: String) {
            //넘어온 값이 없을 때 로그 찍고 리턴
            if(response.isNullOrEmpty()) {
                Toast.makeText(applicationContext,"정인Search_activity", Toast.LENGTH_SHORT).show()
                Log.d("정인Search_Activity", "null in")
                return
            }
            Log.d("정인Search_Activity",response)
            if(!Json().isJson(response)){
                Log.d("퀴즈 입력 통신 에러", response)
                Toast.makeText(applicationContext,"네트워크 통신 오류",Toast.LENGTH_SHORT).show()
                return
            }

            else{
                Log.d("퀴즈 찾기 오류 없음", response)
            }
//            val jsonAry = JSONArray(response)
////            val solve_quiz_list: ArrayList<Quiz> = arrayListOf() //intent 시 넘겨주기 위해 전역 변수로 선언
//            for (i in 0 until jsonAry.length()) {
//                val jsonObj: JSONObject = jsonAry.getJSONObject(i)
//                solve_quiz_list.add(Quiz(jsonObj.getInt("quizid"),jsonObj.getString("question"), jsonObj.getInt("answer")))
//            }
////            rv_solve_quiz.adapter = Res_adapter(@SolveQuizActivity, solve_quiz_list)

        }
    }

    fun SearchQuiz_Click_Listener(view : View){
        when(view.id){
            R.id.btn_solve_quiz ->{
                if(SearchQuiz_Control().edit_check()) {
                    Log.d("입력은 잘됨",sq_et_nickname.text.toString())
                    SearchQuiz_Control().GET_QUIZ(sq_et_nickname.text.toString())
                    Log.d("GET은 잘됨",sq_et_nickname.text.toString())
                    val intent = Intent(this, SolveQuizActivity::class.java)
                    intent.putExtra("nickname", sq_et_nickname.text.toString()) //list를 넘겨주기 위해
                    startActivity(intent)
                }
            }

            R.id.btn_mypage ->{
                val intent = Intent(this, MypageActivity::class.java)
                startActivity(intent)
            }
        }
    }

    inner class EditListener : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            if(!s.isNullOrEmpty()){
                Log.d("입력됨","aaa")
                btn_search.isEnabled = !sq_et_nickname.text.isNullOrEmpty()}
            else{
                Log.d("입력안됨","aaa")
                btn_search.isEnabled = false}
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }
    override fun onPause() {
        asynctask().cancel(true)
        super.onPause()
    }

}