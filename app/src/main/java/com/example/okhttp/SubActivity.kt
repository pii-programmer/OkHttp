package com.example.okhttp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.okhttp.databinding.ActivitySubBinding

class SubActivity:AppCompatActivity() {

    private lateinit var binding: ActivitySubBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val listData = intent.getSerializableExtra("LIST_POSITION")

        when(listData){
            "東京都" -> {
                binding.result.text = "東京都"
            }
            "熊本県" -> {
                binding.result.text = "熊本県"
            }
            "香川県" -> {
                binding.result.text = "香川県"
            }
            "北海道" -> {
                binding.result.text = "北海道"
            }
            "宮城県" -> {
                binding.result.text = "宮城県"
            }
        }
    }
}