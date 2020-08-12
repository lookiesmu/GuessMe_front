package com.example.guessme.data

import com.example.guessme.api.RetrofitInstance
import retrofit2.Response

class QuizRepository {

    suspend fun createQuiz(): Response<QuizList> {
        return RetrofitInstance.api.createQuiz()
    }
}