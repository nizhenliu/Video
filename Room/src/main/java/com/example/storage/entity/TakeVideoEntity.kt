package com.example.storage.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "b_takevideo")
data class TakeVideoEntity(@PrimaryKey(autoGenerate = true) val id:Int,
                           @ColumnInfo val videoLocalPath:String,
                            @ColumnInfo val userid:Int)