package com.example.okhttp

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

// 役割分担：Adapterは表示するデータを受け・渡すだけ
class RecyclerAdapter(private val forecast :MutableList<Forecast>) :RecyclerView.Adapter<ViewHolder>() {

    @SuppressLint("ClickableViewAccessibility")
    // 表示させるタイミングで View を生成する
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int):ViewHolder{
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.row_view,viewGroup,false)

        val viewHolder = ViewHolder(view)

        // RecyclerView にリスナがないから view のリスナを使用
        view.setOnClickListener{
            val position = viewHolder.layoutPosition
            viewHolder.onRowClick(position, forecast)
        }
//        view.setOnTouchListener { _, event ->
//            when (event?.action) {
//                MotionEvent.ACTION_DOWN -> {
//
////                    onRowTouch(rv)
////                    viewHolder.onRowTouch(rv)
//                }
//                MotionEvent.ACTION_UP -> {
//
//                    val position = viewHolder.layoutPosition
//
//                    viewHolder.onRowClick(position, forecast)
//                }
//            }
//            true
//        }
        return viewHolder
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int){

        val forecast = forecast[position]

        viewHolder.bind(forecast, viewHolder)
    }

    // アイテム数を LayoutManager に伝える（生成した View をキャッシュに残してもらうため）
    override fun getItemCount(): Int {
        return forecast.size
    }
}
//
//fun onRowTouch(rv: RecyclerView){
//    val touch = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
//            ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
//    ){
//        // recyclerView = ドラッグ＆ドロップの発生箇所, viewHolder = イベント開始時の座標, target = イベント終了時の座標
//        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
//            val from = viewHolder.adapterPosition
//            val to = target.adapterPosition
//            rv.adapter?.notifyItemMoved(from, to)// アダプターに変更内容を通知
//            return true// 実行
//        }
//        // viewHolder = スワイプされた物, direction = スワイプしたポジション
//        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//            rv.adapter?.notifyItemRemoved(viewHolder.adapterPosition)
//        }
//    })
//    touch.attachToRecyclerView(rv)
//}
//
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