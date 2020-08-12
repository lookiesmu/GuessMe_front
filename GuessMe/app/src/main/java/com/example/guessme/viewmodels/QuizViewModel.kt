package com.example.guessme.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.guessme.data.QuizList
import com.example.guessme.data.QuizRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class QuizViewModel(private val repository: QuizRepository): ViewModel() {

    val quizResponse: MutableLiveData<Response<QuizList>> = MutableLiveData()

    fun creatQuiz() {
        viewModelScope.launch {
            val response =repository.createQuiz()
            quizResponse.value = response
        }
    }
}