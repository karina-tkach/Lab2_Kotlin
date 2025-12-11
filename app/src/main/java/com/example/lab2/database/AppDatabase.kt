package com.example.lab2.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.lab2.R
import com.example.lab2.dao.AppDao
import com.example.lab2.entity.CategoryEntity
import com.example.lab2.entity.TaskEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray

@Database(entities = [CategoryEntity::class, TaskEntity::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database.db"
                )/*.addCallback(PrepopulateRoomCallback(context))*/.build().also { INSTANCE = it }
            }
        }
    }
}

class PrepopulateRoomCallback(private val context: Context) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)

        CoroutineScope(Dispatchers.IO).launch {
            prePopulate(context)
        }
    }

    suspend fun prePopulate(context: Context) {
        try {
            val appDao = AppDatabase.getDatabase(context).appDao()

            val categoriesJson = context.resources
                .openRawResource(R.raw.categories)
                .bufferedReader()
                .readText()

            val categoryArray = JSONArray(categoriesJson)

            for (i in 0 until categoryArray.length()) {
                val obj = categoryArray.getJSONObject(i)
                val category = CategoryEntity(
                    categoryName = obj.getString("category_name")
                )
                appDao.insertCategory(category)
            }

            // Load tasks
            val tasksJson = context.resources
                .openRawResource(R.raw.tasks)
                .bufferedReader()
                .readText()

            val tasksArray = JSONArray(tasksJson)

            for (i in 0 until tasksArray.length()) {
                val obj = tasksArray.getJSONObject(i)
                val task = TaskEntity(
                    title = obj.getString("title"),
                    isDone = obj.getBoolean("is_done"),
                    categoryId = obj.getInt("category_id")
                )
                appDao.insertTask(task)
            }

            Log.e("DB", "Successfully pre-populated categories and tasks")

        } catch (e: Exception) {
            Log.e("DB", "Prepopulate failed: ${e.localizedMessage}")
        }
    }
}