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
import com.example.guessme.data.Json
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
        setContentView(R.layout.activity_search_quiz)
        SearchQuiz_Control().edit_init()

    }

    val solve_quiz_list: ArrayList<Quiz> = arrayListOf()

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
            val nickname = params[1]
            return Okhttp(applicationContext).GET(url)
        }
        override fun onPostExecute(response: String) {
            //넘어온 값이 없을 때 로그 찍고 리턴
            if(response.isNullOrEmpty()) {
                Toast.makeText(applicationContext,"서버 문제 발생", Toast.LENGTH_SHORT).show()
                Log.d("Search_Activity", "null in")
                return
            }
            Log.d("Search_Activity",response)
            /*if(!Json().isJson(response)){
                Log.d("network", response)
                Toast.makeText(applicationContext,"네트워크 통신 오류",Toast.LENGTH_SHORT).show()
                return
            }*/
            val jsonAry = JSONArray(response)
//            val solve_quiz_list: ArrayList<Quiz> = arrayListOf() //intent 시 넘겨주기 위해 전역 변수로 선언
            for (i in 0 until jsonAry.length()) {
                val jsonObj: JSONObject = jsonAry.getJSONObject(i)
                solve_quiz_list.add(Quiz(jsonObj.getInt("quizid"),jsonObj.getString("question"), jsonObj.getInt("answer")))
            }
//            rv_solve_quiz.adapter = Res_adapter(@SolveQuizActivity, solve_quiz_list)

        }
    }

    fun SearchQuiz_Click_Listener(view : View){
        when(view.id){
            R.id.btn_solve_quiz ->{
                if(SearchQuiz_Control().edit_check()) {
                    SearchQuiz_Control().GET_QUIZ(sq_et_nickname.text.toString())

                    val intent = Intent(this, SolveQuizActivity::class.java)
                    intent.putExtra("solveQuizList",solve_quiz_list as Serializable) //list를 넘겨주기 위해
                    startActivity(intent)
                }
            }
        }
    }

    inner class EditListener : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            if(!s.isNullOrEmpty())
                si_btn.isEnabled = !sq_et_nickname.text.isNullOrEmpty()
            else
                si_btn.isEnabled = false
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {       }
    }
    override fun onPause() {
        asynctask().cancel(true)
        super.onPause()
    }

}