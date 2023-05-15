package com.example.storage

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase




object DBUtils {
    lateinit var db: AppDataBase
    fun init(context:Context,dbName: String){
        val MIGRATION_1_2=object : Migration(1,2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("create table b_takevideo(id INTEGER PRIMARY KEY NOT NULL,videoLocalPath TEXT NOT NULL,userid INTEGER NOT NULL)")
            }
        }
        db = Room.databaseBuilder(context, AppDataBase::class.java,dbName)
            .addMigrations(MIGRATION_1_2)
            .build()
    }

    fun getDB(): AppDataBase {
        return db
    }
}
