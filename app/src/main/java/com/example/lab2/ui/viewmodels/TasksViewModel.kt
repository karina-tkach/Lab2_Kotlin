package com.example.lab2.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.lab2.database.AppDatabase
import com.example.lab2.model.Category
import com.example.lab2.model.Task
import com.example.lab2.repository.TaskRepository
import kotlinx.coroutines.launch

class TasksViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = TaskRepository(AppDatabase.getDatabase(application).appDao())

    private val _categories = MutableLiveData<List<Category>>(emptyList())
    val categories: LiveData<List<Category>> = _categories

    private val _tasks = MutableLiveData<List<Task>>(emptyList())
    val tasks: LiveData<List<Task>> = _tasks

    fun loadCategories() {
        viewModelScope.launch {
            _categories.value = repository.getAllCategories()
        }
    }

    fun loadTasks() {
        viewModelScope.launch {
            _tasks.value = repository.getAllTasksWithCategories()
        }
    }

    fun addCategory(name: String) {
        viewModelScope.launch {
            repository.insertCategory(Category(id = 0, categoryName = name))
            loadCategories()
        }
    }

    fun addTask(title: String, category: Category) {
        viewModelScope.launch {
            repository.insertTask(
                Task(id = 0, title = title, isDone = false, category = category)
            )
            loadTasks()
        }
    }

    fun toggleTask(task: Task) {
        viewModelScope.launch {
            val updated = task.copy(isDone = !task.isDone)
            repository.updateTask(updated)
            loadTasks()
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.deleteTask(task)
            loadTasks()
        }
    }

    fun deleteCategory(category: Category) {
        viewModelScope.launch {
            repository.deleteCategory(category)
            loadCategories()
            loadTasks()
        }
    }

    fun deleteAllTasks() {
        viewModelScope.launch {
            repository.clearTasks()
            loadTasks()
        }
    }
}
