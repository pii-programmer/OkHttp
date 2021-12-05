package com.example.okhttp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import androidx.room.migration.Migration
import com.example.okhttp.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.lang.Thread.sleep

class MainActivity : AppCompatActivity() {
    // NullPointerExceptionが発生するので初期化を遅らせる
    private lateinit var binding: ActivityMainBinding
    lateinit var db: AppDatabase
    lateinit var dao: ApiDao
    lateinit var client: OkHttpClient
    lateinit var request: Request

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

// activity_mainに表示させておく
//        val datas = mutableListOf<Forecast>()
//        datas.add(Forecast().apply {
//            date = "sample:2021-12-09"
//            telop = "サンプルテロップ"
//        })
// Adapter
//        binding.listView.adapter = CustomAdapter(this@MainActivity, datas)

        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                /** 遠藤さんコメント **/
                //sleep(20000); // 20秒バックグラウンド処理を待つ
                /** 遠藤さんコメント **/

                // db初期化
                db = Room.databaseBuilder(this@MainActivity, AppDatabase::class.java, "forecast")
                    .addMigrations(MIGRATION_1_2)
                    .build()
                // dao初期化
                dao = db.ApiDao()

                // delete
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
                            val telop = forecastJSON.getString("telop")    // 3日分の"date"と"telop"
                            val detail = forecastJSON.getString("detail")  // "detail"も

                            val forecasts = mutableListOf<Forecast>()
                            forecasts.add(Forecast().apply {
                                this.date = date
                                this.telop = telop
                                this.detail = detail
                            })
                            dao.insertAll(forecasts)
                        }

                        try {
                            val forecastList = dao.selectAll()
                            show(forecast = forecastList)
                            true
                        } catch (e: Exception) {
                            throw e
                        }

                    }
                })
            }
        }
    }
    private fun show(forecast:MutableList<Forecast>) {
        GlobalScope.launch {
            withContext(Dispatchers.Main){
// Adapter
                binding.listView.adapter = CustomAdapter(this@MainActivity, forecast)

// ProgressBar非表示
                binding.mainProgressbar.visibility = android.widget.ProgressBar.INVISIBLE

// ListViewのクリックリスナー
                binding.listView.setOnItemClickListener { parent: AdapterView<*>, view: View, position, id ->

// クリックしたらProgressBar表示
                binding.mainProgressbar.visibility = android.widget.ProgressBar.VISIBLE

                    parent.getItemAtPosition(position) as Forecast

                    Intent(this@MainActivity, SubActivity::class.java).apply {
                        putExtra("ID",id)
                        startActivity(this)
                    }
                }
            }
        }
    }
}

/****  遠藤さんコメント  ****/
// {処理A} Thread.sleep(1000) {処理B} 処理Aを待ってから処理Bを走らせる

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
// }

// cursor = db.query("api_table", arrayOf("text"),null,null,null,null,null)
// cursor.moveToFirst()

// lateinit var handler: Handler
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