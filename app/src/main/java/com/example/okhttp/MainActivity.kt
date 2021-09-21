package com.example.okhttp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.okhttp.databinding.ActivityMainBinding
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var client: OkHttpClient
    lateinit var request: Request
    lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // bindingの初期化
        binding = ActivityMainBinding.inflate(layoutInflater)
        // handlerの初期　メインスレッドを指定
        handler = Handler(Looper.getMainLooper())
        // clientの初期化
        client = OkHttpClient()
        // requestの初期化
        request = Request.Builder().url("https://weather.tsukumijima.net/api/forecast/city/130010").get().build()

        binding.tomorrow.setOnClickListener {
            // バックグラウンドでCallbackメソッド
            client.newCall(request).enqueue(object: Callback{
                // 通信失敗時
                override fun onFailure(call: Call, e: IOException) {
//                    handler.post{
//                        binding.result.text = "Error"
//                    }
                }
                // レスポンス処理
                override fun onResponse(call: Call, response: Response) {
                    handler.post{
                        binding.result.text = "It's OK"
                    }
                }
            })
        }
    }
}