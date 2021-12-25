package com.example.okhttp

import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// 役割分担：ViewHolder で UI部品 と UIの変更点 を管理
class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    var tvDate : TextView = itemView.findViewById(R.id.date)
    var tvTelop : TextView = itemView.findViewById(R.id.telop)

    fun bind (forecast: Forecast, holder: ViewHolder) {

        val forecastDate = forecast.date?.let{

            val index = arrayOf(it.indexOf('-',0))

            index.forEach { i ->
                when(i){
                    4 -> {
                        it.replace("-","年")
                    }
                    7 -> {
                        it.replace("-","月")
                    }
                }
            }

            it.plus("日")
        }

        holder.tvDate.text = forecastDate
        holder.tvTelop.text = forecast.telop
    }

    fun onRowClick(position: Int, forecast: MutableList<Forecast>){
        // itemViewからcontextを取得
        val context = itemView.context

        // クリックしたアイテムのポジション から Forecastの id を認識
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