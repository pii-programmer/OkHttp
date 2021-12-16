package com.example.okhttp

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

// 役割分担：Adapterは表示するデータを受け・渡すだけ
class RecyclerAdapter(private val forecast :MutableList<Forecast>):RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int):ViewHolder{

        // row_view をインフレート
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.row_view,viewGroup,false)

        // ViewHolderのインスタンス生成
        val viewHolder = ViewHolder(view)

        // クリックするアイテムのポジションを取得しておく
        val position = viewHolder.layoutPosition

        // ViewHolderからcontextを取得しておく
        val context = viewHolder.itemView.context

        viewHolder.onRowClick(position, context)

        // ViewHolderを返す
        return viewHolder
    }

    // パラメータ：ViewHolder, アイテムのポジション
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int){

        // アイテムのポジションを取得
        val forecast = forecast[position]

        // ViewHolderでアイテムを割り当てる
        viewHolder.bind(forecast, viewHolder)
    }

    // アイテムの数を返す
    override fun getItemCount(): Int {
        return forecast.size
    }
}
/** RecyclerViewのルール **/
// AdapterはRecyclerView.Adapterクラスを継承する
// 表示するデータをAdapterクラスのコンストラクタにする
// 3つのメソッドをoverrideする（必須）：onCreateViewHolder, onBindViewHolder, getItemCount
/** メモ **/
//val intent = Intent(context, SubActivity::class.java)
//        intent.putExtra("ID", forecast[position].id?.toInt())
//        context.startActivity(intent)