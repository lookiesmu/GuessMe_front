package com.example.guessme.api

import com.example.guessme.data.QuizList
import retrofit2.Response
import retrofit2.http.GET

interface QuizApi {
    // 퀴즈 생성
    @GET("/quizzes")
    suspend fun createQuiz() : Response<QuizList>
}