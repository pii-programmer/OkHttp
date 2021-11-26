package com.example.okhttp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import org.w3c.dom.Text

class CustomAdapter(context: Context, list: MutableList<Forecast>):ArrayAdapter<Forecast>(context,0,list) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var view = convertView

        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.row_view,parent,false)
        }

        val data = getItem(position) as Forecast

// まとめてnullチェック
        view?.let {
            it.findViewById<TextView>(R.id.date).text = data.date
            it.findViewById<TextView>(R.id.telop).text = data.telop
        }

//        when(data.icon){
//            "tokyo" -> {
//                view?.findViewById<ImageView>(R.id.icon)?.setImageResource(R.drawable.tokyo)
//            }
//            "shizuoka" -> {
//                view?.findViewById<ImageView>(R.id.icon)?.setImageResource(R.drawable.shizuoka)
//            }
//            "kagawa" -> {
//                view?.findViewById<ImageView>(R.id.icon)?.setImageResource(R.drawable.kagawa)
//            }
//            "kumamoto" -> {
//                view?.findViewById<ImageView>(R.id.icon)?.setImageResource(R.drawable.kumamoto)
//            }
//            "miyagi" -> {
//                view?.findViewById<ImageView>(R.id.icon)?.setImageResource(R.drawable.miyagi)
//            }
//        }
//        view?.findViewById<TextView>(R.id.prefecture)?.apply { text = data.prefecture }

        return view!!
    }
}

data class Data(
    var icon:String? = null,
    var prefecture:String? = null
)