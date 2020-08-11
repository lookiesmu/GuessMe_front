package com.example.guessme

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        Login_Control().edit_init()
    }
    inner class Login_Control {
        fun edit_init(){
            si_et_nickname.addTextChangedListener(EditListener())
            si_et_password.addTextChangedListener(EditListener())
        }
        fun edit_check() : Boolean{
            if(si_et_nickname.text.isNullOrEmpty() && si_et_password.text.isNullOrEmpty()) {
                Toast.makeText(applicationContext, "아이디와 비밀번호를 입력해주세요", Toast.LENGTH_LONG).show()
                return false
            }
            if (si_et_nickname.text.isNullOrEmpty()) {
                Toast.makeText(applicationContext, "아이디를 입력해주세요", Toast.LENGTH_LONG).show()
                return false
            }
            if (si_et_password.text.isNullOrEmpty()){
                Toast.makeText(applicationContext, "비밀번호를 입력해주세요", Toast.LENGTH_LONG).show()
                return false
            }
            return true
        }
        fun POST_login(id : String, pw : String){
            val url = getString(R.string.server_url) + getString(R.string.signin)
            asynctask().execute(url,id,pw)
        }
    }
    inner class asynctask : AsyncTask<String, Void, String>(){
        override fun doInBackground(vararg params: String): String {
            val url = params[0]
            val id = params[1]
            val pw = params[2]
            return Okhttp(applicationContext).POST(url, Json().login(id,pw))
        }
        override fun onPostExecute(response: String) {
            //넘어온 값이 없을 때 로그 찍고 리턴
            if(response.isNullOrEmpty()) {
                Toast.makeText(applicationContext,"서버 문제 발생",Toast.LENGTH_SHORT).show()
                Log.d("SignIn_Activity", "null in")
                return
            }
            Log.d("SignIn_Activity",response)
            //response 값이 json문이 아니면 통신 오류 메세지 출력
            if(!Json().isJson(response)){
                Toast.makeText(applicationContext,"네트워크 통신 오류",Toast.LENGTH_SHORT).show()
                return
            }
            val user = User(
                si_et_nickname.text.toString()
            )
            if(user.nickname == "null"){
                Toast.makeText(applicationContext,"아이디가 없거나 비밀번호가 틀렸습니다",Toast.LENGTH_SHORT).show()
                return
            }
            User_Control(applicationContext).set_user(user)
            startActivity(Intent(applicationContext, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
            finish()
        }
    }
    //Activity 클릭 리스너
    fun login_Click_Listener(view :View){
        when(view.id){
            R.id.si_et_nickname ->{
                if(Login_Control().edit_check()) {
                    Login_Control().POST_login(si_et_nickname.text.toString(), si_et_password.text.toString())
                }
            }
            R.id.tv_signup ->startActivity(Intent(applicationContext, SignUpActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
        }
    }
    inner class EditListener : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            if(!s.isNullOrEmpty())
                btn_signin.isEnabled = !si_et_nickname.text.isNullOrEmpty() && !si_et_password.text.isNullOrEmpty()
            else
                btn_signin.isEnabled = false
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {       }
    }
    override fun onPause() {
        asynctask().cancel(true)
        super.onPause()
    }
}
