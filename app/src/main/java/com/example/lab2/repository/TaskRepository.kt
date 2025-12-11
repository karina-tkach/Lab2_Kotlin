package com.example.lab2.repository

import com.example.lab2.api.MyApi
import com.example.lab2.api.MyRetrofitClient
import com.example.lab2.dao.AppDao
import com.example.lab2.dto.CategoryDTO
import com.example.lab2.dto.TaskDTO
import com.example.lab2.entity.CategoryEntity
import com.example.lab2.entity.TaskEntity
import com.example.lab2.entity.TaskWithCategory
import com.example.lab2.model.Category
import com.example.lab2.model.Task

class TaskRepository (private val appDao: AppDao) {
    private val myRetrofitClient = MyRetrofitClient.getClient()
    private val myApi = myRetrofitClient.create(MyApi::class.java)

    suspend fun loadTasksFromApi(): List<TaskDTO> {

        val tasksResponse = myApi.getTasks()

        return if (tasksResponse.isSuccessful) {
            tasksResponse.body()?:emptyList()
        } else{
            emptyList()
        }
    }

    suspend fun loadCategoriesFromApi(): List<CategoryDTO> {

        val categoriesResponse = myApi.getCategories()

        return if (categoriesResponse.isSuccessful) {
            categoriesResponse.body()?:emptyList()
        } else{
            emptyList()
        }
    }

    suspend fun getAllCategories(): List<Category> {
        return appDao.getAllCategories().map { it.toDomain() }
    }

    suspend fun getAllTasksWithCategories(): List<Task> {
        return appDao.getTasksWithCategories().map { it.toDomain() }
    }

    suspend fun insertCategory(category: Category) {
        appDao.insertCategory(category.toEntity())
    }

    suspend fun insertTask(task: Task) {
        appDao.insertTask(task.toEntity())
    }

    suspend fun insertTasks(tasks: List<TaskEntity>) {
        appDao.insertAllTasks(tasks = tasks.toTypedArray())
    }

    suspend fun insertCategories(categories: List<CategoryEntity>) {
        return appDao.insertAllCategories(categories)
    }
    suspend fun clearTasks() {
        appDao.deleteAllTasks()
    }

    suspend fun deleteTask(task: Task) {
        appDao.deleteTask(task.toEntity())
    }

    suspend fun deleteCategory(category: Category) {
        appDao.deleteCategory(category.toEntity())
    }

    suspend fun updateTask(task: Task) {
        appDao.updateTask(task.toEntity())
    }
}

    fun Category.toEntity() = CategoryEntity(
        id = this.id,
        categoryName = this.categoryName
    )

    fun CategoryEntity.toDomain() = Category(
        id = this.id,
        categoryName = this.categoryName
    )

    fun Task.toEntity() = TaskEntity(
        id = this.id,
        title = this.title,
        isDone = this.isDone,
        categoryId = this.category.id
    )

    fun TaskWithCategory.toDomain() = Task(
        id = this.id,
        title = this.title,
        isDone = this.isDone,
        category = this.category.toDomain()
    )

    fun TaskDTO.toEntity() = TaskEntity(
        title = title,
        isDone = isDone,
        categoryId = category.id
    )

    fun CategoryDTO.toEntity() = CategoryEntity(
        categoryName = categoryName
    )

    fun CategoryDTO.toDomain() = Category(
        categoryName = categoryName
    )

