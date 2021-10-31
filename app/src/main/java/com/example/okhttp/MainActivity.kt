package com.example.okhttp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.AdapterView
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
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val datas = mutableListOf<Data>()
        val icons = listOf("iconGlass","iconEye","iconNormal")
        val prefectureNames = listOf("東京都","熊本県","香川県","北海道","宮城県")

        for(i in 0..4){
            datas.add(Data().apply {
                when ( i ) {
                    0 -> {
                        icon = icons[0]
                        prefecture = prefectureNames[0]
                    }
                    1 -> {
                        icon = icons[1]
                        prefecture = prefectureNames[1]
                    }
                    2 -> {
                        icon = icons[2]
                        prefecture = prefectureNames[2]
                    }
                    3 -> {
                        icon = icons[1]
                        prefecture = prefectureNames[3]
                    }
                    4-> {
                        icon = icons[0]
                        prefecture = prefectureNames[4]
                    }
                }
            })
        }

        binding.listView.adapter = CustomAdapter(this, datas)

        binding.listView.setOnItemClickListener{ parent:AdapterView<*>, view: View, position, id ->
            val listPosition = parent.getItemAtPosition(position) as Data
//            val iconPosition = listPosition.icon
            val prefecturePosition = listPosition.prefecture

            Intent(this,SubActivity::class.java).apply{
                putExtra("LIST_POSITION", prefecturePosition)
                startActivity(this)
            }
        }

        // handlerの初期　　メインスレッドを指定
        handler = Handler(Looper.getMainLooper())
        // clientの初期化
        client = OkHttpClient()
        // requestの初期化
        request = Request.Builder().url("https://weather.tsukumijima.net/api/forecast/city/130010").get().build()

//        binding.tomorrow.setOnClickListener {
//            // バックグラウンドでCallbackメソッド
//            client.newCall(request).enqueue(object: Callback{
//                // 通信失敗時
//                override fun onFailure(call: Call, e: IOException) {
//                        handler.post{
//                            binding.result.text = "Error"
//                        }
//                }
//                // レスポンス処理
//                override fun onResponse(call: Call, response: Response) {
//                    handler.post{
//                        binding.result.text = "It's OK"
//                    }
//                }
//            })
//        }
    }
}