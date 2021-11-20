package com.example.okhttp

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.okhttp.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {
    // NullPointerExceptionを発生させないためonCreate前にlateinit
    private lateinit var binding: ActivityMainBinding
    // 初期化が後になるものをlateinit
    lateinit var DB: AppDatabase
    lateinit var dao: ApiDao
    lateinit var client: OkHttpClient
    lateinit var request: Request
//    lateinit var cursor: Cursor
//    lateinit var handler: Handler

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

        GlobalScope.launch {
            withContext(Dispatchers.IO) {

                // DB初期化
                DB = Room.databaseBuilder(this@MainActivity, AppDatabase::class.java, "api_table").build()
                // dao初期化
                dao = DB.ApiDao()

                dao.deleteAll()

                // client初期化
                client = OkHttpClient()

                // request初期化
                request = Request.Builder().url("https://weather.tsukumijima.net/api/forecast?city=130010").get().build()

                client.newCall(request).enqueue(object : Callback {
                    override fun onFailure(
                        call: Call,
                        e: IOException
                    ) { //メインスレッド以外でUIを変更するとExceptionが発生する
                    }

                    override fun onResponse(call: Call, response: Response) {
                        val jsonObject = JSONObject(response.body?.string())
                        val resultText =
                            jsonObject.getJSONObject("description")["bodyText"] as String

                        val results = mutableListOf<API>()
                        results.add(API(0, "今日の天気").apply {
                            id = 1
                            text = resultText
                        })

                        try {
                            dao.insert(results)
                            val select = dao.selectAll()
                            show(result = select as MutableList<API>)
                            true
                        } catch (e: Exception) {
                            throw e
                        }
                    }
                })
            }
        }
    }
    private fun show(result: MutableList<API>) {
        GlobalScope.launch {
            withContext(Dispatchers.Main){

//                api 見たい時はここのコメントアウト外す
//                binding.apiText.text = result.toString()

                // ListViewのクリックリスナー
                binding.listView.setOnItemClickListener { parent: AdapterView<*>, view: View, position, id ->

                    val listPosition = parent.getItemAtPosition(position) as Data
                    val prefecturePosition = listPosition.prefecture
                    val icon = listPosition.icon

                    // fixme:ClassCastException: com.example.okhttp.Data cannot be cast to com.example.okhttp.API
                    val idPosition = parent.getItemAtPosition(position) as API
                    val text = idPosition.text

                    Intent(this@MainActivity, SubActivity::class.java).apply {
                        putExtra("LIST_POSITION", prefecturePosition)
                        putExtra("ICON", icon)
                        putExtra("API_TEXT", text)
                        startActivity(this)
                    }
                }
            }
        }
    }
}
//cursor = DB.query("api_table", arrayOf("text"),null,null,null,null,null)
//cursor.moveToFirst()
// {処理A} Thread.sleep(1000) {処理B} 処理Aを待ってから処理Bを走らせる
// handlerの初期化
//                handler = Handler(Looper.getMainLooper())
//                IPHostConvert().constructor() //unknownHostException No address associatedの対策
//                        val postExecutor = HandlerPostExecutor(View)
//                        handler.post(postExecutor)
//
//                    Intent(this@MainActivity, SubActivity::class.java).apply {
//                        putExtra("RESULT_TEXT", result.toString())
//                        startActivity(this)
//                    }