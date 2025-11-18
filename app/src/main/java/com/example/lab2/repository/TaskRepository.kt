package com.example.lab2.repository

import com.example.lab2.model.Task

class TaskRepository {

    fun getTasks(): List<Task> {
        return listOf(
            Task(1, "Buy groceries", false),
            Task(2, "Call mom", false),
            Task(3, "Finish homework", true),
            Task(4, "Clean the room", false)
        )
    }
}
