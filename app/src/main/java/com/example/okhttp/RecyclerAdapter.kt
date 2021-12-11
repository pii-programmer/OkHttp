package com.example.okhttp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

// RecyclerAdapterクラスの役割は、各アイテム(tvDate と tvTelop)にデータを割り当てること。
// だから表示させたいデータを ViewHolder から受け取り、RecyclerAdapterクラスのコンストラクタにしてる。
class RecyclerAdapter(private val forecast :MutableList<Forecast>):RecyclerView.Adapter<ViewHolder>() {

    // 各アイテム(tvDate と tvTelop)のロジック
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int):ViewHolder{

        // 一行分の row_view を作成
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.row_view,viewGroup,false)



        // ViewHolderを返す
        return ViewHolder(view)
    }

    // ViewHolderと各アイテムのポジションが渡ってくる
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int){

        // 表示するアイテムのポジションを取得
        val forecast = forecast[position]

        // row_view の tvDate と tvTelop に割り当てる
        viewHolder.tvDate.text = forecast.date
        viewHolder.tvTelop.text = forecast.telop
    }

    // 表示するのに必要なアイテムの数を返す
    override fun getItemCount(): Int {
        return forecast.size
    }
}