package com.example.okhttp

import android.os.Bundle
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
    private lateinit var binding: ActivityMainBinding
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
//         keyはユニーク
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
//        binding.listView.adapter = CustomAdapter(this, datas)

        GlobalScope.launch {
            withContext(Dispatchers.IO) {

                // DB初期化
                DB = Room.databaseBuilder(this@MainActivity, AppDatabase::class.java, "forecast").build()
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
                    ) {
                    }

                    override fun onResponse(call: Call, response: Response) {
                        val jsonObject = JSONObject(response.body?.string())
// result
                        val forecastJSONArray = jsonObject.getJSONArray("forecasts")  // "forecasts"はJSONObjectのJSONArray
                        for (i in 0 until forecastJSONArray.length()){
                            val forecastJSON = forecastJSONArray.getJSONObject(i)
                            val date = forecastJSON.getString("date")
                            val telop = forecastJSON.getString("telop")  // 3日分の"date"と"telop"

                            val forecasts = mutableListOf<Forecast>()
                            forecasts.add(Forecast().apply {
                                this.date = date
                                this.telop = telop
                            })
                            dao.insertAll(forecasts)
                        }
                        try {
//                            val insert = dao.insertAll(forecasts)
//                            print(insert)
                            val forecastList = dao.selectAll()
                            print(forecastList)
                            show()
                            true
                        } catch (e: Exception) {
                            throw e
                        }
                    }
                })
            }
        }
    }
    private fun show() {
        GlobalScope.launch {
            withContext(Dispatchers.Main){
// Adapter
//                binding.listView.adapter = CustomAdapter(this@MainActivity, forecastList as MutableList<Forecast>)

// ListViewのクリックリスナー
//                binding.listView.setOnItemClickListener { parent: AdapterView<*>, view: View, position, id ->
//
//                    val dummyPosition = parent.getItemAtPosition(position) as Forecast
//                    val send = dummyPosition.telop
//
//                    Intent(this@MainActivity, SubActivity::class.java).apply {
//                        putExtra("DUMMY_POSITION", send)
////                        putExtra("ICON", icon)
////                        putExtra("API_TEXT", text)
//                        startActivity(this)
//                    }
//                }
            }
        }
    }
}
/****  遠藤さんコメント  ****/
// val forecasts = mutableListOf<Forecast>()
/****  遠藤さんコメント  ****/

// try {
//     dao.insertAll(forecast)
//     val select = dao.selectAll()
//     show(result = select as MutableList<API>)
//     true
// } catch (e: Exception) {
//     throw e
// }
// private fun show(result: MutableList<API>) {

// val resultText =
//     jsonObject.getJSONObject("description")["bodyText"] as String
// val results = mutableListOf<API>()
// results.add(API(0, "今日の天気").apply {
//     id = 1
//     text = resultText
// })

// cursor = DB.query("api_table", arrayOf("text"),null,null,null,null,null)
// cursor.moveToFirst()

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

// Forecast型で１データ入れてみたがBoolean型になってしまいClassCastException
//                val dummy = mutableListOf<Forecast>()
//                val dummyList = dummy.add(Forecast().apply {
//                    id = 1
//                    date = "2021/11/23"
//                    telop = "曇り" }) as MutableList<Forecast>