package com.example.okhttp

import android.content.Intent
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

// 役割分担：ViewHolder で View管理
class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

    var tvDate : TextView = view.findViewById(R.id.date)
    var tvTelop : TextView = view.findViewById(R.id.telop)

    fun bind (forecast: Forecast, holder: ViewHolder) {
        holder.tvDate.text = forecast.date
        holder.tvTelop.text = forecast.telop
    }

//    fun onRowTouch(rv: RecyclerView){
//        val touch = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
//            ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
//        ){
//            // recyclerView = ドラッグ＆ドロップの発生箇所, viewHolder = イベント開始時の座標, target = イベント終了時の座標
//            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
//                val from = viewHolder.adapterPosition
//                val to = target.adapterPosition
//                rv.adapter!!.notifyItemMoved(from, to)// アダプターに変更内容を通知
//                return true// 実行
//            }
//            // viewHolder = スワイプされた物, direction = スワイプしたポジション
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                rv.adapter!!.notifyItemRemoved(viewHolder.adapterPosition)
//            }
//        })
//        touch.attachToRecyclerView(rv)
//    }

    fun onRowClick(position: Int, forecast: MutableList<Forecast>){

        val context = itemView.context

        val intent = Intent(context, SubActivity::class.java)
        intent.putExtra("ID", forecast[position].id)
        context.startActivity(intent)

    }
}
/** ViewHolderのルール **/
// ViewHolderはRecyclerView.ViewHolderクラスを継承する
// RecyclerView.ViewHolder は Viewオブジェクト を必要としている　＝　RecyclerView.ViewHolderの引数
// RecyclerView.ViewHolder では itemView という名の Viewオブジェクト をコンストラクタにしてる
// その itemView を ViewHolder のパラメータにする
//
/** メモ **/
//    // タッチイベントを監視する
//    class TouchListener : RecyclerView.SimpleOnItemTouchListener(){
//        override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
//            Log.d("TouchTest","InterceptOK")
//
//            val touch = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
//                ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
//            ){
//                // ドラッグだったら
//                override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
//                    Log.d("TouchTest","onMove")
//                    val from = viewHolder.adapterPosition
//                    val to = target.adapterPosition
//                    rv.adapter?.notifyItemMoved(from, to)
//                    return true
//                }
//                // スワイプだったら
//                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                }
//            })
//            // SimpleCallbackの実行
//            touch.attachToRecyclerView(rv)
//            Log.d("TouchTest","attachToRecyclerView")
//
//            return super.onInterceptTouchEvent(rv, e)
//            Log.d("TouchTest","returnIntercept")
//        }
//    }