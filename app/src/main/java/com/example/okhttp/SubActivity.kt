package com.example.okhttp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.okhttp.databinding.ActivitySubBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SubActivity : BaseActivity() {
    private lateinit var binding: ActivitySubBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubBinding.inflate(layoutInflater)
        setContentView(binding.root)

        GlobalScope.launch {
            val result = withContext(Dispatchers.IO) {

                val id = intent.getIntExtra("ID",0)
                dao.select(id)
            }

            withContext(Dispatchers.Main) {

                val detailTextView = TextView(this@SubActivity).apply {
                    text = result.replace(",","\n\n\n")
                            .replace("{","")
                            .replace("}","")
                            .replace(":","\n")
                            .replace("\"","")
                            .replace("weather","◆天気は")
                            .replace("wind","◆風向きは")
                            .replace("wave","◆風速は")
                }
                binding.DetailRelativeLayout.addView(detailTextView)

                // "今日の天気は${whether}です。\n\n風向きは${wind}です。\n\n風速は${wave}です。良い一日を"
            }
        }
    }
}
/** 以下メモ **/
// ナビゲーションバーの ◀︎ は onDestroy()？
// binding.backButton.setOnClickListener{
//     Intent(this@SubActivity,MainActivity::class.java).apply {
//         startActivity(this)
//     }
// }
//
// 動的レイアウト
//                for (i in 0 until forecast.size) {
//                    when{
//                        (i==0 && position=="0") -> {
//                            val forecastRecord = forecast[0]
//                            val tv0 = TextView(this@SubActivity).apply {
//                                text = forecastRecord.detail
//                                setTextColor(Color.parseColor("green"))
//                            }
//                            binding.detailToday.addView(tv0)
//                        }
//                        (i==1 && position=="1") -> {
//                            val forecastRecord = forecast[1]
//                            val tv1 = TextView(this@SubActivity).apply {
//                                text = forecastRecord.detail
//                                setTextColor(Color.parseColor("red"))
//                            }
//                            binding.detailTomorrow.addView(tv1)
//                        }
//                        (i==2 && position=="2") -> {
//                            val forecastRecord = forecast[2]
//                            val tv2 = TextView(this@SubActivity).apply {
//                                text = forecastRecord.detail
//                                setTextColor(Color.parseColor("blue"))
//                            }
//                            binding.detailDayAfterTomorrow.addView(tv2)
//                        }
//                    }
//                }