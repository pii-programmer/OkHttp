package com.example.okhttp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {
    lateinit var db: AppDatabase
    lateinit var dao: ApiDao

    // アプリの起動や再起動時・画面の回転時に Activity は生成される
    // その際 savedInstanceState:Bundle に値が入ってくる
    // 普段は null なので null許容にしてる
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // db初期化
        // getDatabase メソッド = AppDatabaseクラス で定義した Database のインスタンスをアプリ内で一つだけにするメソッド
        // getDatabase メソッドに 基底Activity の context を渡す
        db = AppDatabase.getDatabase(this)

        // dao初期化
        dao = db.ApiDao()
    }
}