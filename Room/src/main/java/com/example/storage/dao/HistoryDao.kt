package com.example.storage.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.storage.entity.HistoryEntity


@Dao
interface HistoryDao {

    @Query("SELECT * FROM b_history order by id DESC LIMIT 5")
    fun getAll():List<HistoryEntity>

    @Query("SELECT * FROM b_history order by id DESC LIMIT (:page*:pagesize),:pagesize")
    fun getAllByPage(page: Int,pagesize:Int):List<HistoryEntity>

    @Insert
    fun insertAll(entity: HistoryEntity)

    @Delete
    fun delete(entity: HistoryEntity)
}