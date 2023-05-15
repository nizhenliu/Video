package com.example.storage.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "b_history")
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true) val id:Int,
    @ColumnInfo val type:Int,
    @ColumnInfo val title:String,
    @ColumnInfo val primaryImg:String,
    @ColumnInfo val content:String,
    @ColumnInfo val authorImg:String,
    @ColumnInfo val authorName:String
)
