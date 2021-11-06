package com.example.okhttp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.AdapterView
import androidx.room.Room
import com.example.okhttp.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import okhttp3.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {
    // 処理が重くなる変数、頻発の変数をlateinitに
    private lateinit var binding: ActivityMainBinding
    lateinit var DB: AppDatabase
    lateinit var dao: ApiDao
    lateinit var client: OkHttpClient
    lateinit var request: Request
    lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val datas = mutableListOf<Data>()
        // keyはユニーク
        val maps = mapOf(
            "tokyo" to "東京都",
            "kumamoto" to "熊本県",
            "kagawa" to "香川県",
            "shizuoka" to "静岡県",
            "miyagi" to "宮城県"
        )

        for ((key, value) in maps) {
            datas.add(Data().apply {
                icon = key
                prefecture = value
            })
        }
        binding.listView.adapter = CustomAdapter(this, datas)

        // DBの初期化
        DB = Room.databaseBuilder(this, AppDatabase::class.java, "API_table").build()
        // daoの初期化
        dao = DB.ApiDao()
        // clientの初期化
        client = OkHttpClient()
        // requestの初期化
        request =
            Request.Builder().url("https://weather.tsukumijima.net/api/forecast?city=130010").get()
                .build()
        // handlerの初期化
         handler = Handler(Looper.getMainLooper())

        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                IPHostConvert().constructor() //fixme: unknownException No address associatedの対策

                dao.deleteAll()

                client.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) { //メインスレッド以外でUIを変更するとExceptionが発生する
//                        val postExecutor = HandlerPostExecutor(View)
//                        handler.post(postExecutor)
                    }

                    override fun onResponse(call: Call, response: Response) {
                        val jsonObject = JSONObject(response.body?.string())
                        val resultText = jsonObject.getJSONObject("description")["bodyText"] as String
                        val results = mutableListOf<API>()
                        results.add(API(1,"今日の天気").apply {
                            text = resultText
                        })
                        dao.insert(results)
                    }
                })
                val resultList = dao.selectAll()
                binding.apiText.text = resultList.toString()  //TODO
            }
            withContext(Dispatchers.Main) {
                binding.listView.setOnItemClickListener { parent: AdapterView<*>, view: View, position, id ->
                    val listPosition = parent.getItemAtPosition(position) as Data
                    val prefecturePosition = listPosition.prefecture

                    Intent(this@MainActivity, SubActivity::class.java).apply {
                        putExtra("LIST_POSITION", prefecturePosition)
                        startActivity(this)
                    }
                }
            }
        }
    }
}

//                    Intent(this@MainActivity, SubActivity::class.java).apply {
//                        putExtra("RESULT_TEXT", result.toString())
//                        startActivity(this)
//                    }