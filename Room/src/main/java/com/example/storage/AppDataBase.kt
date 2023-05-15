package com.example.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.storage.dao.HistoryDao
import com.example.storage.dao.TakeVideoDao
import com.example.storage.entity.HistoryEntity
import com.example.storage.entity.TakeVideoEntity


@Database(entities = [HistoryEntity::class, TakeVideoEntity::class],version = 2, exportSchema = false)
abstract class AppDataBase:RoomDatabase() {
    abstract fun historyDao(): HistoryDao
    abstract fun takeVideoDao(): TakeVideoDao
}