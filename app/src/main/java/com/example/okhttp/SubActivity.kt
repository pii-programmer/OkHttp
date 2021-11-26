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

        val sendDummy = intent.getStringExtra("DUMMY_POSITION")
        binding.result.text = sendDummy

    }
}
//val listData = intent.getSerializableExtra("LIST_POSITION")
//        when(listData){
//            "東京都" -> {
//                binding.result.text = "東京都"
//            }
//            "熊本県" -> {
//                binding.result.text = "熊本県"
//            }
//            "香川県" -> {
//                binding.result.text = "香川県"
//            }
//            "静岡県" -> {
//                binding.result.text = "静岡県"
//            }
//            "宮城県" -> {
//                binding.result.text = "宮城県"
//            }
//        }
//
//        val iconData = intent.getStringExtra("ICON")
//        when(iconData){
//            "tokyo" -> {
//                binding.subIcon.setImageResource(R.drawable.tokyo)
//            }
//            "kumamoto" -> {
//                binding.subIcon.setImageResource(R.drawable.kumamoto)
//            }
//            "shizuoka" -> {
//                binding.subIcon.setImageResource(R.drawable.shizuoka)
//            }
//            "kagawa" -> {
//                binding.subIcon.setImageResource(R.drawable.kagawa)
//            }
//            "miyagi" -> {
//                binding.subIcon.setImageResource(R.drawable.miyagi)
//            }
//        }
//
//        val bodyText = intent.getStringExtra("API_TEXT")
//        binding.apiText.text = bodyText
//    }
//val apiData = intent.getSerializableExtra("RESULT_TEXT")
//binding.result.text = apiData.toString()