package com.example.okhttp

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

        // CustomActivityにする
        GlobalScope.launch {
            val forecast = withContext(Dispatchers.IO) {

                db = Room.databaseBuilder(this@SubActivity, AppDatabase::class.java, "forecast")
                    .addMigrations(MIGRATION_1_2)
                    .build()

                dao = db.ApiDao()

                dao.selectAll()

            }

            withContext(Dispatchers.Main) {
                val id = intent.getLongExtra("ID", 0)

                for (i in 0 until forecast.size) {
                    when{
                        (i==0 && id==0.toLong()) -> {
                            val forecastRecord = forecast[0]
                            val tv0 = TextView(this@SubActivity).apply {
                                text = forecastRecord.detail
                                setTextColor(Color.parseColor("green"))
                            }
                            binding.detailRelativeLayout0.addView(tv0)
                        }
                        (i==1 && id==1.toLong()) -> {
                            val forecastRecord = forecast[1]
                            val tv1 = TextView(this@SubActivity).apply {
                                text = forecastRecord.detail
                                setTextColor(Color.parseColor("red"))
                            }
                            binding.detailRelativeLayout1.addView(tv1)
                        }
                        (i==2 && id==2.toLong()) -> {
                            val forecastRecord = forecast[2]
                            val tv2 = TextView(this@SubActivity).apply {
                                text = forecastRecord.detail
                                setTextColor(Color.parseColor("blue"))
                            }
                            binding.detailRelativeLayout2.addView(tv2)
                        }
                    }
                }
            }
        }
    }
}