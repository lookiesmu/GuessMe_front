package com.example.guessme

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.guessme.data.Quiz
import kotlinx.android.synthetic.main.activity_solve_quiz.*
import java.util.logging.Logger.global

class SolveQuizActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_solve_quiz)

        val solve_quiz_list = intent.getSerializableExtra("solveQuizList")
        rv_solve_quiz.adapter = SolveQuizAdapter(this, solve_quiz_list as ArrayList<Quiz>)
    }





}