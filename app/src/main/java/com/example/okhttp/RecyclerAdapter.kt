package com.example.okhttp

import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

// 役割分担：Adapterは表示するデータを受け・渡すだけ
class RecyclerAdapter(private val forecast :MutableList<Forecast>):RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int):ViewHolder{

        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.row_view,viewGroup,false)

        val viewHolder = ViewHolder(view)

        val rv = RecyclerView(viewHolder.itemView.context)

        view.setOnTouchListener { _, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {// タッチしたら onTouch を実行
                    Log.d("touchTest","touch")

                    viewHolder.onTouch(adapter = this, recyclerView = rv)
                    Log.d("touchTest","attach")
                }
                MotionEvent.ACTION_UP -> {// 指を離したらクリックとして認識。onRowClick を実行
                    Log.d("clickTest","clickTest")
                    val position = viewHolder.layoutPosition

                    viewHolder.onRowClick(position, forecast)
                }
            }
            true
        }

        return viewHolder
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int){

        val forecast = forecast[position]

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
//        // クリックしたら
//        view.setOnClickListener{
//            // クリックしたアイテムのポジション取得
//            val position = viewHolder.layoutPosition
//
//            // onRowClick 発動
//            viewHolder.onRowClick(position,forecast)
//        }
//
//val intent = Intent(context, SubActivity::class.java)
//        intent.putExtra("ID", forecast[position].id?.toInt())
//        context.startActivity(intent)
//
//        // ViewHolderからcontextを取得しておく
//        val context = viewHolder.itemView.context