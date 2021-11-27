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
    lateinit var DB: AppDatabase
    lateinit var dao: ApiDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubBinding.inflate(layoutInflater)
        setContentView(binding.root)

        GlobalScope.launch {
            val forecast = withContext(Dispatchers.IO) {

                DB = Room.databaseBuilder(this@SubActivity, AppDatabase::class.java, "forecast")
                    .addMigrations(MIGRATION_1_2)
                    .build()

                dao = DB.ApiDao()

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
//                                getColor(R.color.purple_200)
                            }
                            binding.detailRelativeLayout0.addView(tv0)
                        }
                        (i==1 && id==1.toLong()) -> {
                            val forecastRecord = forecast[1]
                            val tv1 = TextView(this@SubActivity).apply {
                                text = forecastRecord.detail
                                setTextColor(Color.parseColor("red"))
//                                getColor(R.color.teal_700)
                            }
                            binding.detailRelativeLayout1.addView(tv1)
                        }
                        (i==2 && id==2.toLong()) -> {
                            val forecastRecord = forecast[2]
                            val tv2 = TextView(this@SubActivity).apply {
                                text = forecastRecord.detail
                                setTextColor(Color.parseColor("blue"))
//                                getColor(R.color.purple_700)
                            }
                            binding.detailRelativeLayout2.addView(tv2)
                        }
                    }
                }
            }
        }
    }
}