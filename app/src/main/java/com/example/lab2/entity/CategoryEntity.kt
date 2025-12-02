package com.example.lab2.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "categories")
data class CategoryEntity(
    @PrimaryKey(
        autoGenerate = true
    )
    @ColumnInfo("category_id")
    val id: Int = 0,

    @ColumnInfo(name = "category_name")
    var categoryName: String
)