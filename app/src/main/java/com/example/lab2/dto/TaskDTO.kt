package com.example.lab2.dto

data class TaskDTO(
    val id: Int,
    val title: String,
    val isDone: Boolean,
    val category: CategoryDTO
)