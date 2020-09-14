package com.example.tableseatingtest1.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "customer")
data class Customer (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = "table_id")
    var tableId: Long = 0L,

    @ColumnInfo(name = "name")
    var customerName: String = ""
)