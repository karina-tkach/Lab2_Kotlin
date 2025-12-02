package com.example.lab2.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded

data class TaskWithCategory(
    @ColumnInfo(name = "task_id")
    val id: Int = 0,

    val title: String,

    @ColumnInfo("is_done")
    val isDone: Boolean,

    @Embedded val category: CategoryEntity
)