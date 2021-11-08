package com.example.okhttp

import android.util.Log
import java.net.InetAddress
import java.net.UnknownHostException

class IPHostConvert {
    fun constructor(): String? {
            val inetAddress = InetAddress.getByName("weather.tsukimijima.net") //指定したホスト名のinetAddressオブジェクトを生成
            val ipAddress = inetAddress.hostAddress //IPアドレスを返す
            return ipAddress!!
    }
}
// このクラスを作った理由:UnKownHostException
// No address associated with host:"weather.tsukimijima.net" だったため
//TODO: IPアドレスを指定する（IPアドレスが無いからExceptionが起きている?）