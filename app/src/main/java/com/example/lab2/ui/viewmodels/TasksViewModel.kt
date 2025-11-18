package com.example.lab2.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab2.model.Task
import com.example.lab2.repository.TaskRepository
import kotlinx.coroutines.launch

class TasksViewModel : ViewModel() {
    private val repository = TaskRepository()

    private val _tasks = MutableLiveData<List<Task>>(emptyList())
    val tasks: LiveData<List<Task>> = _tasks

    fun fetchTasks() {
        viewModelScope.launch {
            val list = repository.getTasks()
            _tasks.postValue(list)
        }
    }

    fun toggleTask(id: Int) {
        val currentList = _tasks.value

        val updatedList = currentList?.map { task ->
            if (task.id == id) task.copy(isDone = !task.isDone) else task
        }

        _tasks.postValue(updatedList)
    }
}
