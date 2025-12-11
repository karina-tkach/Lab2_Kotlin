package com.example.lab2.model

data class Task(
    val id: Int = 0,
    val title: String,
    val isDone: Boolean,
    val category: Category
)