package com.example.guessme

import android.content.Context
import android.util.Log
import okhttp3.*
import java.io.IOException

class Okhttp() {
    var context : Context? = null
    val client : OkHttpClient = OkHttpClient()
    lateinit var response: Response

    fun GET(url: String):String{
        try {
            val builder= Request.Builder()
                .url(url)
                .get()
            val request = builder.build()
            response = client.newCall(request).execute()
            return response.body()?.string()!!
        }catch (e: IOException){
            return e.toString()
        }
    }

    fun POST(url: String, jsonbody: String):String{
        try {
            val builder= Request.Builder()
                .url(url)
                .post(RequestBody.create(MediaType.parse("application/json"), jsonbody))
            val request = builder.build()
            response = client.newCall(request).execute()
            return response.body()?.string()!!
        }catch (e: IOException){
            return e.toString()
        }
    }

    fun DELETE(url: String, jsonbody: String):String{
        try {
            val builder= Request.Builder()
                .url(url)
                .delete(RequestBody.create(MediaType.parse("application/json"), jsonbody))
            Log.d("Okhttp",jsonbody)

            val request = builder.build()
            response = client.newCall(request).execute()
            return response.body()?.string()!!
        }catch (e: IOException){
            return e.toString()
        }
    }

    fun PUT(url: String, jsonbody: String):String{
        try {
            val builder= Request.Builder()
                .url(url)
                .put(RequestBody.create(MediaType.parse("application/json"), jsonbody))

            val request = builder.build()
            response = client.newCall(request).execute()
            return response.body()?.string()!!
        }catch (e: IOException){
            return e.toString()
        }
    }
}