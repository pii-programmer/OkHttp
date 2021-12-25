package com.example.okhttp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import java.io.Serializable

@Dao
interface ApiDao {
    @Insert
    fun insertAll(forecasts: MutableList<Forecast>)

    @Query("SELECT * FROM forecast")
    fun selectAll():MutableList<Forecast>

    @Query("SELECT detail FROM forecast WHERE id = :id")
    fun select(id: Int):String

    @Query("DELETE FROM forecast")
    fun deleteAll()
}
/** dao **/
// interfaceは抽象クラスの親玉
// interfaceに定義されるメソッドは、自動的に public abstract メソッドになる。
// interfaceはフィールドを持たない
// interface内のメソッドはメニューであり、メニューのロジックはオーバーライド先で行う。
// メソッドで値を返す時、値の型に気をつける！！