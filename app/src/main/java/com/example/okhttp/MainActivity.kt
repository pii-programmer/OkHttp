package com.example.okhttp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.okhttp.databinding.ActivityMainBinding
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var client: OkHttpClient
    lateinit var request: Request

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // bindingの初期化
        binding = ActivityMainBinding.inflate(layoutInflater)
        // clientの初期化
        client = OkHttpClient()
        // requestの初期化
        request = Request.Builder().url("https://weather.tsukumijima.net/api/forecast/city/130010").build()

        // バックグラウンドでCallbackメソッド
        client.newCall(request).enqueue(object: Callback{
            // 通信失敗時
            override fun onFailure(call: Call, e: IOException) {
                println("Error")
            }
            // レスポンス処理
            override fun onResponse(call: Call, response: Response) {
                println("It's OK")
            }
        })
        binding.tomorrow.setOnClickListener {
            binding.result.text = "Hello World"
        }
    }
}