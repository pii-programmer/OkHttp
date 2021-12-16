package com.example.okhttp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.okhttp.databinding.ActivitySubBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json.Default.context

class SubActivity:AppCompatActivity() {
    private lateinit var binding: ActivitySubBinding
    lateinit var db: AppDatabase
    lateinit var dao: ApiDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // TODO:3 DBの初期化は1回だけ。DB削除して再初期化する時は、アプリ側でDBを更新する必要がある時だけ。
        GlobalScope.launch {
            val result = withContext(Dispatchers.IO) {

                db = Room.databaseBuilder(this@SubActivity, AppDatabase::class.java, "forecast")
                    .addMigrations(MIGRATION_1_2)
                    .build()

                dao = db.ApiDao()

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


            }
        }
    }
}

// ナビゲーションバーの ◀︎ は onDestroy()？
// binding.backButton.setOnClickListener{
//     Intent(this@SubActivity,MainActivity::class.java).apply {
//         startActivity(this)
//     }
// }
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