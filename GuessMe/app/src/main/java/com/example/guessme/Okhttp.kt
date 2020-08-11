package com.example.guessme


import android.content.Context
import android.util.Log
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.util.concurrent.TimeUnit

class Okhttp() {
    var context : Context? = null
    val client : OkHttpClient = OkHttpClient()
    var token : String? = null
    init {
        client.newBuilder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5,TimeUnit.SECONDS)
            .writeTimeout(5,TimeUnit.SECONDS)
            .build()
    }
    constructor(context : Context) : this(){
        this.context = context
        token = User_Control(context).get_token()
    }

    fun GET(url: String):String{
        try {
            val builder= Request.Builder()
                .url(url)
                .get()
            val request = builder.build()
            if(!token.isNullOrEmpty())
                builder.header("Authorization",token!!)

            var response : Response = client.newCall(request).execute()
            return response.body!!.string()
        }catch (e: IOException){
            return e.toString()
        }
    }

    fun POST(url: String, jsonbody: String):String{
        try {
            val builder= Request.Builder()
                .url(url)
                .post(jsonbody.toRequestBody("application/json".toMediaTypeOrNull()))
            if(!token.isNullOrEmpty())
                builder.header("Authorization",token!!)
            val request = builder.build()
            var response : Response = client.newCall(request).execute()
            if(!response.header("Authorization").isNullOrEmpty())
                User_Control(context!!).set_token(response.header("Authorization").toString())
            return response.body?.string()!!
        }catch (e: IOException){
            return e.toString()
        }
    }

    fun DELETE(url: String, jsonbody: String):String{
        try {
            val builder= Request.Builder()
                .url(url)
                .delete(jsonbody.toRequestBody("application/json".toMediaTypeOrNull()))
            Log.d("Okhttp",jsonbody)
            if(!token.isNullOrEmpty())
                builder.header("Authorization",token!!)

            val request = builder.build()
            var response : Response = client.newCall(request).execute()
            return response.body?.string()!!
        }catch (e: IOException){
            return e.toString()
        }
    }

    fun PUT(url: String, jsonbody: String):String{
        try {
            val builder= Request.Builder()
                .url(url)
                .put(jsonbody.toRequestBody("application/json".toMediaTypeOrNull()))
            if(!token.isNullOrEmpty())
                builder.header("Authorization",token!!)

            val request = builder.build()
            var response : Response = client.newCall(request).execute()
            return response.body?.string()!!
        }catch (e: IOException){
            return e.toString()
        }
    }
}