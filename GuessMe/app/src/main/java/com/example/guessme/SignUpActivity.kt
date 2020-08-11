package com.example.guessme

import android.app.AlertDialog
import com.example.guessme.R
import com.example.guessme.Loading_Dialog
import android.os.AsyncTask
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.guessme.SignUpActivity.SignUp_Control
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.json.JSONObject

class SignUpActivity : AppCompatActivity() {
    var user: User? = null
    var checkId: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        su_et_nickname.addTextChangedListener(EditListener())
    }

    inner class SignUp_Control {
        fun edit_check(): Boolean {
            //필수 사항 EditText id값 array
            val editArray =
                arrayListOf(R.id.su_et_nickname, R.id.su_et_password, R.id.su_et_passwordcheck)
            for (i in editArray) {//해당 EditText 값이 비었는지 체크
                val editText: EditText = findViewById(i)
                if (editText.text.isNullOrEmpty()) {
                    Toast.makeText(applicationContext, "필수 정보를 입력해주세요", Toast.LENGTH_SHORT).show()
                    return false
                }
            }
            //1차 비밀번호와 2차 비밀번호 같은지 확인
            if (!su_et_password.text.toString().equals(su_et_passwordcheck.text.toString())) {
                Toast.makeText(applicationContext, "1차 2차 비밀번호가 다릅니다", Toast.LENGTH_SHORT).show()
                return false
            }
            return true
        }

        fun GET_Check(id: String) {
            val url = getString(R.string.server_url) + getString(R.string.checkid) + id
            asynctask().execute("0", url)
        }

        fun POST_SignUp(pw: String) {
            val url = getString(R.string.server_url) + getString(R.string.signup)
            asynctask().execute("1", url, pw)
        }

        fun Dialog_Signup() {
            val builder = AlertDialog.Builder(this@SignUpActivity)
            builder.setMessage("회원가입 성공")
            builder.setCancelable(false)
            builder.setPositiveButton("확인") { dialog, which -> finish() }
            builder.show()
        }

    }

    inner class asynctask : AsyncTask<String, Void, String>() {
        var state: Int = -1 //state == 0 : GET_아이디 중복확인, state == 1 : POST_회원가입
        var loadingDialog = Loading_Dialog(this@SignUpActivity)
        override fun onPreExecute() {

        }

        override fun doInBackground(vararg params: String): String {
            state = Integer.parseInt(params[0])
            val url = params[1]
            var response: String = ""
            when (state) {
                0 -> response = Okhttp().GET(url)
                1 -> {
                    val pw = params[2]
                    response = Okhttp().POST(url, Json().signup(user, pw))
                }
            }
            return response
        }

        override fun onPostExecute(response: String) {
            if (response.isEmpty()) {
                Log.d("SignUp_Activity", "null")
                return
            }
            if (!Json().isJson(response)) {
                Toast.makeText(applicationContext, "네트워크 통신 오류", Toast.LENGTH_SHORT).show()
                Log.d("SignUp_Activity", response)
                return
            }

            val jsonObj = JSONObject(response)
            when (state) {
                0 -> {
                    if (jsonObj.getBoolean("success")) {
                        Toast.makeText(applicationContext, "사용가능한 닉네임입니다", Toast.LENGTH_SHORT)
                            .show()
                        checkId = true
                        //signup_signup_Button.isEnabled = true
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


    //Activity 클릭 리스너
    fun Signup_Click_Listener(view: View) {
        when (view.id) {
            R.id.su_duplecheck -> {
                val id = su_et_nickname.text.toString()
                if (id.isEmpty())
                    Toast.makeText(applicationContext, "닉네임를 입력해주세요", Toast.LENGTH_SHORT).show()
                else {
                    SignUp_Control().GET_Check(id)
                }
            }
            R.id.su_btn -> {
                if (!checkId) {
                    Toast.makeText(applicationContext, "중복확인이 필요합니다", Toast.LENGTH_SHORT).show()
                    return
                }
                if (SignUp_Control().edit_check()) {
                    val id = su_et_password.text.toString()
                    val pw = su_et_passwordcheck.text.toString()
                    user = User(id)
                    SignUp_Control().POST_SignUp(pw)
                }
            }
        }
    }

    inner class EditListener : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            su_duplecheck.isEnabled = !s.isNullOrEmpty()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            checkId = false
        }
    }

    override fun onPause() {
        asynctask().cancel(true)
        super.onPause()
    }
}