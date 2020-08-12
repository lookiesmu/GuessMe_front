package com.example.guessme

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.guessme.data.QuizRepository
import com.example.guessme.viewmodels.QuizViewModel
import com.example.guessme.viewmodels.QuizViewModelFactory
import kotlinx.android.synthetic.main.activity_create_quiz.*

class CreateQuizActivity : AppCompatActivity() {

    private lateinit var viewModel: QuizViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_quiz)

        val repository = QuizRepository()
        val viewModelFactory = QuizViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(QuizViewModel::class.java)
        viewModel.creatQuiz()
        viewModel.quizResponse.observe(this, Observer { response ->
            if(response.isSuccessful){
                Log.d("Response", response.body()?.quiz1.toString())
                Log.d("Response", response.body()?.quiz2.toString())
                Log.d("Response", response.body()?.quiz3.toString())
                Log.d("Response", response.body()?.quiz4.toString())
                Log.d("Response", response.body()?.quiz5.toString())

            }else{
                Log.d("Response", response.errorBody()?.toString())
            }
        })

    }

}