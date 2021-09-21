package com.example.okhttp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {
    lateinit var client: OkHttpClient
    lateinit var request: Request

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        client = OkHttpClient()
        request = Request.Builder().url("https://weather.tsukumijima.net/api/forecast/city/130010").build()
        client.newCall(request).enqueue(object: Callback{
            override fun onFailure(call: Call, e: IOException) {
                println("Error")
            }
            override fun onResponse(call: Call, response: Response) {
                println("It's OK")
            }
        })
    }
}