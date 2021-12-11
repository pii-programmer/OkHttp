package com.example.okhttp

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// ViewHolderクラスにUI部品をまとめておく
// row_view の一行分が itemView として渡ってきて、それをViewHolderクラスのコンストラクタにする
class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    // プロパティ（暗黙的getterとsetter）
    var tvDate : TextView = itemView.findViewById(R.id.date)
    var tvTelop : TextView = itemView.findViewById(R.id.telop)

}