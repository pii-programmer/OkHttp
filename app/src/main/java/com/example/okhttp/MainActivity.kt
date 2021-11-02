package com.example.okhttp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import com.example.okhttp.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import okhttp3.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var client: OkHttpClient
    lateinit var request: Request

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val datas = mutableListOf<Data>()
        // kayはユニーク
        val maps = mapOf(
            "tokyo" to "東京都",
            "kumamoto" to "熊本県",
            "kagawa" to "香川県",
            "shizuoka" to "静岡県",
            "miyagi" to "宮城県")

        for((key,value) in maps){
            datas.add(Data().apply {
                icon = key
                prefecture = value
            })
        }
        binding.listView.adapter = CustomAdapter(this, datas)

        // clientの初期化
        client = OkHttpClient()
        // requestの初期化
        request = Request.Builder().url("https://weather.tsukumijima.net/api/forecast?city=130010").get().build()

        GlobalScope.launch{
            withContext(Dispatchers.IO){
                client.newCall(request).enqueue(object: Callback{
                    override fun onFailure(call: Call, e: IOException) {
                        binding.apiText.text = e.toString()
                    }

                    override fun onResponse(call: Call, response: Response) {
                        val jsonObject = JSONObject(response.body?.string())
                        val bodyText = jsonObject.getJSONObject("description")["bodyText"]
                        binding.apiText.text = bodyText.toString()
                    }
                })
            }
            withContext(Dispatchers.Main){
                binding.listView.setOnItemClickListener{ parent:AdapterView<*>, view: View, position, id ->
                    val listPosition = parent.getItemAtPosition(position) as Data
                    val prefecturePosition = listPosition.prefecture

                    Intent(this@MainActivity,SubActivity::class.java).apply{
                        putExtra("LIST_POSITION", prefecturePosition)
                        startActivity(this)
                    }
                }
            }
        }
    }
}