package com.example.okhttp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {
    lateinit var db: AppDatabase
    lateinit var dao: ApiDao

    // savedInstanceState = 初回は null。 view の再生成時に値が入る
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // setContentView は不要

        // db初期化
        // getDatabase = AppDatabaseクラス で定義したメソッド。Database のインスタンスをアプリ内で一つだけにしてる
        db = AppDatabase.getDatabase(this)

        // dao初期化
        dao = db.ApiDao()
    }
}