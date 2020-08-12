package com.example.guessme

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.guessme.adapters.MyPageAdapter
import com.example.guessme.api.Json
import com.example.guessme.api.Okhttp
import com.example.guessme.data.Quiz
import com.example.guessme.data.Rank
import com.example.guessme.util.Constants
import org.json.JSONArray
import org.json.JSONObject

class MypageActivity : AppCompatActivity() {

    val rank_url = Constants.BASE_URL + "/users/rank"
    val quiz_url = Constants.BASE_URL + "/quizzes"
    val rankList: ArrayList<Rank> = arrayListOf()

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    val context : Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage)
        Log.d("Mypage_Activity", "1")

        Mypage_Control().GET_Rank()
        //setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_mypage_edit, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.btn_delete_quiz -> {
                Mypage_Control().DELETE_Quiz()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    inner class Mypage_Control {
        // 서버로부터 차트 get
        fun GET_Rank(){
            Log.d("Mypage_Activity","2")
            asynctask().execute("0", rank_url)
        }

        fun DELETE_Quiz(){
            asynctask().execute("1", quiz_url)
        }

        // response rv에 띄우기
        fun show_Quiz(context: Context){
            Log.d("Mypage_Activity", "6")
            viewManager = LinearLayoutManager(context)
            viewAdapter = MyPageAdapter(rankList)

            recyclerView = findViewById<RecyclerView>(R.id.rv_rank).apply {
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
    fun Mypage_Click_Listener(view : View){
        when(view.id){
            R.id.btn_solve_quiz_mp -> {        // 퀴즈 생성 완료 버튼
                    val intent = Intent(this, SearchQuizActivity::class.java)
                    startActivity(intent)
            }
        }
    }

    inner class asynctask : AsyncTask<String, Void, String>() {
        var state=-1        // 0: get_차트조회, 1: delete_퀴즈삭제
        override fun onPreExecute() {
            // 사전
        }

        override fun doInBackground(vararg params: String): String {
            state = Integer.parseInt(params[0])
            val url=params[1]
            var response: String = ""
            when(state) {
                0 -> {
                    Log.d("Mypage_Activity","3")
                    response = Okhttp().GET(url)
                }
                1 -> {
                    response= Okhttp().DELETE(url,Json()
                        .deleteQuiz())
                }
            }
            Log.d("Mypage_Activity","4")

            return response
        }

        override fun onPostExecute(response: String) {
            if(response.isNullOrEmpty()) {
                Toast.makeText(applicationContext,"서버 문제 발생", Toast.LENGTH_SHORT).show()
                Log.d("Mypage_Activity", "null in")
                return
            }
            if(!Json().isJson(response)){
                Log.d("Mypage_Activity", "제이슨 아님")
                Toast.makeText(applicationContext,"네트워크 통신 오류", Toast.LENGTH_SHORT).show()
                return
            }
//            Log.d("Mypage_Activity",response)

            var jsonArr = JSONArray(response)
            when(state) {
                0 -> {// 차트 조회
                    for (i in 0 until jsonArr.length()) {
                        var jsonObj: JSONObject = jsonArr.getJSONObject(i)
                        rankList.add(Rank(jsonObj.getInt("index"),jsonObj.getString("nickname"), jsonObj.getInt("score")))
                    }

                    // response rv에 띄우기
                    Mypage_Control().show_Quiz(context)
                }
                1 -> {// 퀴즈 삭제 후 퀴즈생성 액티비티 이동
                    Toast.makeText(applicationContext, "성공적으로 퀴즈를 삭제하였습니다!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(context, CreateQuizActivity::class.java)
                    startActivity(intent)
                }
            }
            Log.d("Mypage_Activity", "5")
        }
    }



}