package com.example.lab2.dto

data class Task(
    val id: Int,
    val title: String,
    val isDone: Boolean,
    val category: CategoryDTO
)