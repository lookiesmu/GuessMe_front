package com.example.guessme

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.guessme.adapters.CreateQuizAdapter
import com.example.guessme.api.Json
import com.example.guessme.api.Okhttp
import com.example.guessme.data.Quiz
import com.example.guessme.util.Constants.Companion.BASE_URL
import org.json.JSONArray
import org.json.JSONObject

class CreateQuizActivity : AppCompatActivity() {

    val quiz_url = BASE_URL + "/quizzes"
    val createQuizList: ArrayList<Quiz> = arrayListOf()

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    val context : Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_quiz)
        Log.d("CreateQuiz_Activity","1")


        CreateQuiz_Control().GET_CreateQuiz()




    }

    inner class CreateQuiz_Control {
        // 서버로부터 퀴즈 문항 get
        fun GET_CreateQuiz(){
            Log.d("CreateQuiz_Activity","2")

            asynctask().execute("0", quiz_url)

        }

        // 퀴즈 생성
        fun POST_CreateQuiz(){
            asynctask().execute("1", quiz_url, createQuizList.toString())
        }

        // 선택했는지 확인
        fun select_check() : Boolean {
            for (createQuiz in createQuizList){
                if(createQuiz.answer == -1) {
                    return false
                }
            }
            return true
        }

        // response rv에 띄우기
        fun show_Quiz(context: Context){
            Log.d("CreateQuiz_Activity", "6")
            viewManager = LinearLayoutManager(context)
            viewAdapter = CreateQuizAdapter(createQuizList)

            recyclerView = findViewById<RecyclerView>(R.id.rv_create_quiz).apply {
                // use this setting to improve performance if you know that changes
                // in content do not change the layout size of the RecyclerView
                setHasFixedSize(true)

                // use a linear layout manager
                layoutManager = viewManager

                // specify an viewAdapter (see also next example)
                adapter = viewAdapter
        }
    }

    }

    // 버튼 클릭 리스너
    fun CreateQuiz_Click_Listener(view : View){
        when(view.id){
            R.id.btn_create_quiz -> {        // 퀴즈 생성 완료 버튼
                if (CreateQuiz_Control().select_check()) {
                    CreateQuiz_Control().POST_CreateQuiz()

                    val intent = Intent(this, SearchQuizActivity::class.java)
                    startActivity(intent)
                } else{
                    Toast.makeText(applicationContext,"모든 항목에 답변해주세요.",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    inner class asynctask : AsyncTask<String, Void, String>() {
        var state: Int = -1 //state == 0 : GET_퀴즈조회, state == 1 : POST_퀴즈생성
        override fun onPreExecute() {
            // 사전
        }

        override fun doInBackground(vararg params: String): String {
            state = Integer.parseInt(params[0])
            val url = params[1]
            var response: String = ""
            Log.d("CreateQuiz_Activity","3")

            when (state) {
                0 -> {
                    Log.d("CreateQuiz_Activity","4")
                    response = Okhttp().GET(url)
                }
                1 -> {
                     var quizList = params[2]

                    response = Okhttp().POST(url, Json()
                        .createQuiz(quizList))

                }
            }
            return response
        }

        override fun onPostExecute(response: String) {
            if(response.isNullOrEmpty()) {
                Toast.makeText(applicationContext,"서버 문제 발생", Toast.LENGTH_SHORT).show()
                Log.d("CreateQuiz_Activity", "null in")
                return
            }
            if(!Json().isJson(response)){
                Log.d("CreateQuiz_Activity", "제이슨 아님")
                Toast.makeText(applicationContext,"네트워크 통신 오류",Toast.LENGTH_SHORT).show()
                return
            }
            //Log.d("CreateQuiz_Activity",response)

            var jsonArr = JSONArray(response)
            when (state) {
                0 -> {
                    Log.d("CreateQuiz_Activity", "5")
                    for (i in 0 until jsonArr.length()) {
                        var jsonObj: JSONObject = jsonArr.getJSONObject(i)
                        createQuizList.add(Quiz(jsonObj.getInt("quizid"),jsonObj.getString("question"), jsonObj.getInt("answer")))
                    }

                    // response rv에 띄우기
                    CreateQuiz_Control().show_Quiz(context)

                }
                1 -> {
                    Toast.makeText(applicationContext, "성공적으로 퀴즈를 생성했습니다!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}