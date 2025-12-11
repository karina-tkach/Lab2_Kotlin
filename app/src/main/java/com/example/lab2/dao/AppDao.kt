package com.example.lab2.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.lab2.entity.CategoryEntity
import com.example.lab2.entity.TaskEntity
import com.example.lab2.entity.TaskWithCategory

@Dao
interface AppDao {

    @Transaction
    @Query("""
        SELECT * FROM tasks join categories On tasks.category_id = categories.category_id ORDER BY task_id DESC
    """)
    suspend fun getTasksWithCategories(): List<TaskWithCategory>

    @Insert
    suspend fun insertTask(task: TaskEntity)

    @Insert
    suspend fun insertAllTasks(vararg tasks: TaskEntity)

    @Update
    suspend fun updateTask(task: TaskEntity)

    @Delete
    suspend fun deleteTask(task: TaskEntity)

    @Query("DELETE FROM tasks")
    suspend fun deleteAllTasks()

    // categories
    @Query("""
        SELECT * FROM categories
    """)
    suspend fun getAllCategories(): List<CategoryEntity>

    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun insertCategory(category: CategoryEntity)

    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun insertAllCategories(categories: List<CategoryEntity>)

    @Delete
    suspend fun deleteCategory(category: CategoryEntity)
}