package com.example.okhttp

import android.app.ProgressDialog.show
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
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
    // coordinatorLayoutだとinflateにしてrootを取得し、onCreate時にrootを渡す。
    // NullPointerExceptionを発生させないためonCreate前にlateinit
    private lateinit var binding: ActivityMainBinding
    // 処理が重くなる変数をlateinit(事前に変数を定義しておくことでonCreate時の処理が初期化だけになる)
    lateinit var DB: AppDatabase
    lateinit var dao: ApiDao
    lateinit var client: OkHttpClient
    lateinit var request: Request
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

        // DBの初期化
        DB = Room.databaseBuilder(this, AppDatabase::class.java, "api_table").build()
        // daoの初期化
        dao = DB.ApiDao()

        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                dao.deleteAll()

                // clientの初期化
                client = OkHttpClient()
                // requestの初期化
                request =
                        Request.Builder().url("https://weather.tsukumijima.net/api/forecast?city=130010").get()
                                .build()
                // handlerの初期化
//                handler = Handler(Looper.getMainLooper())
//                IPHostConvert().constructor() //unknownHostException No address associatedの対策

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
                        try{
                            dao.insert(results)
                            val select = dao.selectAll()
                            show(result = select as MutableList<API>)
                            true
                        } catch (e: Exception){
                            throw e
                        }
                    }
                })
            }
//            withContext(Dispatchers.Main) {
//                val resultList = dao.selectAll()
//                binding.apiText.text = resultList.toString()
//                binding.listView.setOnItemClickListener { parent: AdapterView<*>, view: View, position, id ->
//                    val listPosition = parent.getItemAtPosition(position) as Data
//                    val prefecturePosition = listPosition.prefecture
//
//                    Intent(this@MainActivity, SubActivity::class.java).apply {
//                        putExtra("LIST_POSITION", prefecturePosition)
//                        startActivity(this)
//                    }
//                }
//            }
        }
    }
    private fun show(result: MutableList<API>) {
        GlobalScope.launch {
            withContext(Dispatchers.Main){
                binding.apiText.text = result.toString()

//                val adapter = ArrayAdapter(this@MainActivity, R.layout.api_row_view, result)
//                binding.apiListView.adapter = adapter

                // ListViewのクリックリスナー
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


// {処理A} Thread.sleep(1000) {処理B} 処理Aを待ってから処理Bを走らせる

//                    Intent(this@MainActivity, SubActivity::class.java).apply {
//                        putExtra("RESULT_TEXT", result.toString())
//                        startActivity(this)
//                    }