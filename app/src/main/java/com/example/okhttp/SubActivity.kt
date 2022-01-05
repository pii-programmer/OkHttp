package com.example.okhttp

import android.os.Bundle
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
            // タップした detail を GlobalScope のプロパティに
            var selectDetail: String?

            withContext(Dispatchers.IO) {
                val id = intent.getIntExtra("ID", 0)

                selectDetail = dao.select(id)
                    .replace("(", "")
                    .replace(")", "")
            }

            withContext(Dispatchers.Main) {
                // detail を String から 配列に戻す
                val detailArray = selectDetail?.split(",")

                detailArray?.let { detail ->
                    binding.detailWeather.text = detail[0]
                    binding.detailWind.text = detail[1]
                    binding.detailWave.text = detail[2]
                }
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
// String から 配列に
// val detailArray = selectDetail?.split(",")
//          detailArray?.forEach { detail ->
//              print(detail)
//          }
// index No.0 1 2 をデバッグで確認した
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
// 動的レイアウト
//                val detailTextView = TextView(this@SubActivity).apply {
//                    text = result.replace(",","\n\n\n")
//                            .replace("{","")
//                            .replace("}","")
//                            .replace(":","\n")
//                            .replace("\"","")
//                            .replace("weather","◆天気は")
//                            .replace("wind","◆風向きは")
//                            .replace("wave","◆風速は")
//                }
//                binding.DetailRelativeLayout.addView(detailTextView)