package com.example.okhttp

import android.content.Intent
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

// 役割分担：ViewHolder で UI管理
class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

    var tvDate : TextView = view.findViewById(R.id.date)
    var tvTelop : TextView = view.findViewById(R.id.telop)

    fun bind (forecast: Forecast, holder: ViewHolder) {
        // Dateの加工に失敗
//        val forecastDate = forecast.date?.let{ date ->
//            val year = date.indexOf("-",4)
//            val month = date.indexOf("-",7)
//
//        }
        holder.tvDate.text = forecast.date
        holder.tvTelop.text = forecast.telop
    }

    fun onRowClick(position: Int, forecast: MutableList<Forecast>){
        // ViewHolder の抽象クラスのプロパティ itemView から context を取得。 final だから確実に context を取得できる？
        val context = itemView.context

        // クリックしたアイテムのポジション から Forecastの id を認識
        val intent = Intent(context, SubActivity::class.java)
        intent.putExtra("ID", forecast[position].id)
        context.startActivity(intent)

    }

    // タッチイベントを監視する
    class TouchListener : RecyclerView.SimpleOnItemTouchListener(){
        override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
            Log.d("TouchTest","InterceptOK")

            val touch = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
            ){
                // ドラッグだったら
                override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                    val from = viewHolder.adapterPosition
                    val to = target.adapterPosition
                    rv.adapter?.notifyItemMoved(from, to)
                    return true
                }
                // スワイプだったら
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                }
            })
            // SimpleCallbackの実行
            touch.attachToRecyclerView(rv)

            return true
        }
    }

}
/** ViewHolderのルール **/
// ViewHolderはRecyclerView.ViewHolderクラスを継承する
// RecyclerView.ViewHolder は Viewオブジェクト を必要としている　＝　RecyclerView.ViewHolderの引数
// RecyclerView.ViewHolder では itemView という名の Viewオブジェクト をコンストラクタにしてる
// その itemView を ViewHolder のパラメータにする
//
// メモ
//fun onTouch(adapter: RecyclerAdapter, recyclerView: RecyclerView){
//        // コールバックオブジェクト生成 ドラッグは上下、スワイプは左右
//        val touch = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
//            ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
//        ){
//            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
//                val from = viewHolder.adapterPosition
//                val to = target.adapterPosition
//                adapter.notifyItemMoved(from, to)
//                return true
//            }
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//            }
//        })
//        // コールバックを実行
//        touch.attachToRecyclerView(recyclerView)
//    }