package com.example.okhttp

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.annotation.UiThread
import com.example.okhttp.databinding.ActivityMainBinding

//class HandlerPostExecutor(convertView: View?) :Runnable {
//    private val apiText = convertView?.findViewById<TextView>(R.id.api_text)
//
//    @UiThread
//    override fun run() {
//        apiText?.text = "通信エラー"
//    }
//}

//メインスレッド以外で行いたいUIの変更をここに