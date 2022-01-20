package com.example.okhttp

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.okhttp.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var client: OkHttpClient
    lateinit var request: Request

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        GlobalScope.launch {
            withContext(Dispatchers.IO) {
//                sleep(20000); // 20秒バックグラウンド処理を待つ

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

                        val forecastJSONArray = jsonObject.getJSONArray("forecasts")  // "forecasts"はJSONObjectのJSONArray

                        for (i in 0 until forecastJSONArray.length()){
                            val forecastJSON = forecastJSONArray.getJSONObject(i) // 3日分の"date"と"telop"と"detail"
                            val date = forecastJSON.getString("date")
                            val telop = forecastJSON.getString("telop")

                            val detailObject = forecastJSON.getJSONObject("detail")

                            // detail の中身を Triple に
                            val detail = Triple(
                                detailObject.getString("weather"),
                                detailObject.getString("wind"),
                                detailObject.getString("wave"))

                            val forecasts = mutableListOf<Forecast>()
                            forecasts.add(Forecast().apply {
                                this.date = date
                                this.telop = telop
                                this.detail = detail.toString()
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

                // ProgressBar 非表示
                binding.mainProgressbar.visibility = android.widget.ProgressBar.INVISIBLE

                // LayoutManager でレイアウト作成
                binding.recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)

                // Adapterをセット
                val recyclerAdapter = RecyclerAdapter(forecast)
                binding.recyclerView.adapter = recyclerAdapter

                // あたらしくrecyclerAdapter をよぶのではなく、今生きてる recyclerAdapter に変更点を加える
                // binding.recyclerView も同じく
                val itemTouchHelper = ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(
                    ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT){

                    // recyclerView = ドラッグ＆ドロップの発生箇所
                    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                        val from = viewHolder.adapterPosition
                        val to = target.adapterPosition
                        recyclerAdapter.notifyItemMoved(from, to)
                        return true
                    }

                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        recyclerAdapter.notifyItemRemoved(viewHolder.adapterPosition)
                    }
                })
                itemTouchHelper.attachToRecyclerView(binding.recyclerView)
                }
            }
        }
    }
/** メモ **/
//                // インターセプトしてタッチイベント実行
//                binding.recyclerView.addOnItemTouchListener(ViewHolder.TouchListener())
// db初期化
//                db = Room.databaseBuilder(this@MainActivity, AppDatabase::class.java, "forecast")
//                    .addMigrations(MIGRATION_1_2)
//                    .build()
// dao初期化
//                dao = getDatabase(this@MainActivity).ApiDao()
//
// {処理A} Thread.sleep(1000) {処理B} 処理Aを待ってから処理Bを走らせる
//
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
//
// cursor = db.query("api_table", arrayOf("text"),null,null,null,null,null)
// cursor.moveToFirst()
//
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
//
// activity_mainに表示させておく
//        val datas = mutableListOf<Forecast>()
//        datas.add(Forecast().apply {
//            date = "sample:2021-12-09"
//            telop = "サンプルテロップ"
//        })
//
// Adapter
// ListView に Adapter をセット
//                binding.listView.adapter = CustomAdapter(this@MainActivity, forecast)
//
// ListView は TextView に直接クリックリスナを設定できる
//                binding.listView.setOnItemClickListener { parent: AdapterView<*>, view: View, position, id ->
//                    parent.getItemAtPosition(position) as Forecast
//                    Intent(this@MainActivity, SubActivity::class.java).apply {
//                        putExtra("ID",id)
//                        startActivity(this)
//                    }
//        binding.listView.adapter = CustomAdapter(this@MainActivity, datas)
//
// Forecast型で１データ入れてみたがBoolean型になってしまいClassCastException
//                val dummy = mutableListOf<Forecast>()
//                val dummyList = dummy.add(Forecast().apply {
//                    id = 1
//                    date = "2021/11/23"
//                    telop = "曇り" }) as MutableList<Forecast>