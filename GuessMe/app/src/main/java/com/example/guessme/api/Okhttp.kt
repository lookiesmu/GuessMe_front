package com.example.guessme.api


import android.content.Context
import android.util.Log
import okhttp3.*
import java.io.IOException
import java.util.concurrent.TimeUnit


class Okhttp() {
    var context : Context? = null
    val client : OkHttpClient = OkHttpClient()
    var token : String? = null
    lateinit var response: Response
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
                .addHeader("Content-Type", "application/json")
//                .addHeader("X-AUTH-TOKEN", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIzIiwicm9sZXMiOltdLCJpYXQiOjE1OTcyNDk2ODgsImV4cCI6MTU5NzI1MzI4OH0.TaLOPZ2o3ec58I9EAGa99_gBkqHjpuboQG6m_6yeC9c")
                .get()
            if(!token.isNullOrEmpty())
                builder.header("X-AUTH-TOKEN",token!!)
            val request = builder.build()
            var response : Response = client.newCall(request).execute()
            Log.d("networ", token)
            return response.body()!!.string()

        }catch (e: IOException){
            return e.toString()
        }
    }

    fun POST(url: String, jsonbody: String):String{
        try {
            val builder= Request.Builder()
                .url(url)
                .post(RequestBody.create(MediaType.parse("application/json"), jsonbody))

            if(!token.isNullOrEmpty())
                builder.header("X-AUTH-TOKEN",token!!)
            Log.d("network","tok3: "+ token)
            val request = builder.build()
            if(!request.header("X-AUTH-TOKEN").isNullOrEmpty())
                User_Control(context!!)
                    .set_token(request.header("X-AUTH-TOKEN").toString())
            response = client.newCall(request).execute()
            if(!response.header("X-AUTH-TOKEN").isNullOrEmpty())
                User_Control(context!!)
                    .set_token(response.header("X-AUTH-TOKEN").toString())

            return response.body()!!.string()

        }catch (e: IOException){
            return e.toString()
        }
    }

    fun DELETE(url: String/*, jsonbody: String*/):String{
        try {
            val builder= Request.Builder()
                .url(url)
//                .delete(RequestBody.create(MediaType.parse("application/json"), jsonbody))
                .delete()

            //Log.d("Okhttp",jsonbody)
            if(!token.isNullOrEmpty())
                builder.header("X-AUTH-TOKEN",token!!)

            val request = builder.build()
            var response : Response = client.newCall(request).execute()
            return response.body()!!.string()
        }catch (e: IOException){
            return e.toString()
        }
    }

    fun PUT(url: String, jsonbody: String):String{
        try {
            val builder= Request.Builder()
                .url(url)
                .put(RequestBody.create(MediaType.parse("application/json"), jsonbody))
            if(!token.isNullOrEmpty())
                builder.header("X-AUTH-TOKEN",token!!)

            val request = builder.build()
            var response : Response = client.newCall(request).execute()
            return response.body()!!.string()
        }catch (e: IOException){
            return e.toString()
        }
    }
}