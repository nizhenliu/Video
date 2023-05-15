package com.example.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.storage.entity.TakeVideoEntity


@Dao
interface TakeVideoDao {

    @Query("SELECT * FROM b_takevideo order by id desc")
    fun getAll():List<TakeVideoEntity>

    @Insert
    fun insertAll(vararg videoEntity: TakeVideoEntity)
}