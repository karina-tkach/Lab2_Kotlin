package com.example.lab2.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "tasks",
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = arrayOf("category_id"),
            childColumns = arrayOf("category_id"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TaskEntity(
    @PrimaryKey(
        autoGenerate = true
    )
    @ColumnInfo("task_id")
    val id: Int = 0,

    val title: String,

    @ColumnInfo("is_done")
    val isDone: Boolean,

    @ColumnInfo(name = "category_id", index = true)
    val categoryId: Int
)