package com.example.lab2.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lab2.model.Category
import com.example.lab2.model.Task
import com.example.lab2.ui.viewmodels.TasksViewModel

sealed class TasksScreenRoute(val route: String) {
    object Main : TasksScreenRoute("tasks_main")
    object CategoriesScreen : SettingsScreenRoute("tasks_categories")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreen(
    modifier: Modifier,
    viewModel: TasksViewModel = viewModel()
) {
    val navController = rememberNavController()

    LaunchedEffect(Unit) {
        viewModel.loadCategories()
        viewModel.loadTasks()
    }

    NavHost(
        navController = navController,
        startDestination = TasksScreenRoute.Main.route,
        modifier = modifier
    ) {

        composable(TasksScreenRoute.Main.route) {
            TasksMainScreen(
                modifier,
                viewModel,
                onNext = { navController.navigate(TasksScreenRoute.CategoriesScreen.route) }
            )
        }

        composable(TasksScreenRoute.CategoriesScreen.route) {
            CategoriesScreen(
                modifier,
                viewModel,
                onPrev = {navController.popBackStack()}
            )
        }
    }
}

@Composable
fun TasksMainScreen(modifier: Modifier, viewModel: TasksViewModel, onNext: ()-> Unit) {
    val categories by viewModel.categories.observeAsState(emptyList())
    val tasks by viewModel.tasks.observeAsState(emptyList())

    var newTaskTitle by rememberSaveable { mutableStateOf("") }
    var selectedCategoryIndex by rememberSaveable { mutableIntStateOf(0) }
    var expanded by remember { mutableStateOf(false) }

    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    val headerContent: @Composable () -> Unit = {
        TaskHeader(
            newTaskTitle = newTaskTitle,
            onTitleChange = { newTaskTitle = it },
            categories = categories,
            selectedCategoryIndex = selectedCategoryIndex,
            onCategorySelected = { selectedCategoryIndex = it },
            onAddTask = {
                val cat = categories.getOrNull(selectedCategoryIndex)
                if (newTaskTitle.isNotBlank() && cat != null) {
                    viewModel.addTask(newTaskTitle, cat)
                    newTaskTitle = ""
                }
            },
            expanded = expanded,
            onExpandedChange = { expanded = it },
            onCategories = onNext,
            onDeleteAllTasks = {viewModel.deleteAllTasks()}
        )
    }

    LazyColumn(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (isPortrait) {
            stickyHeader {
                headerContent()
            }
        } else {
            item {
                headerContent()
            }
        }
        items(tasks) { task ->
            TaskItem(
                modifier = Modifier.fillMaxWidth(),
                task = task,
                onToggle = { viewModel.toggleTask(task) },
                onDelete = { viewModel.deleteTask(task) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskHeader(
    newTaskTitle: String,
    onTitleChange: (String) -> Unit,
    categories: List<Category>,
    selectedCategoryIndex: Int,
    onCategorySelected: (Int) -> Unit,
    onAddTask: () -> Unit,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onCategories: () -> Unit,
    onDeleteAllTasks: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(bottom = 8.dp)
    ) {

        Text("Add Task", style = MaterialTheme.typography.titleLarge)

        OutlinedTextField(
            value = newTaskTitle,
            onValueChange = onTitleChange,
            label = { Text("Task title") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { onExpandedChange(!expanded) }
        ) {
            val selected = categories.getOrNull(selectedCategoryIndex)

            OutlinedTextField(
                value = selected?.categoryName ?: "No categories",
                onValueChange = {},
                readOnly = true,
                label = { Text("Category") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(MenuAnchorType.PrimaryNotEditable, true)
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { onExpandedChange(false) }
            ) {
                categories.forEachIndexed { index, cat ->
                    DropdownMenuItem(
                        text = { Text(cat.categoryName) },
                        onClick = {
                            onCategorySelected(index)
                            onExpandedChange(false)
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(10.dp))
        Button(
            onClick = onAddTask,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Task")
        }

        Spacer(Modifier.height(10.dp))
        OutlinedButton (
            onClick = onCategories,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Watch All Categories")
        }

        Spacer(Modifier.height(10.dp))
        Button(
            onClick = onDeleteAllTasks,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red,
                contentColor = Color.White
            )
        ) {
            Text("Delete All Tasks")
        }
    }
}


@Composable
fun TaskItem(
    modifier: Modifier,
    task: Task,
    onToggle: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors()
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Checkbox(
                checked = task.isDone,
                onCheckedChange = { onToggle() }
            )

            Spacer(Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    task.title,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    task.category.categoryName,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete"
                )
            }
        }
    }

}


@Composable
fun CategoriesScreen(modifier: Modifier, viewModel: TasksViewModel, onPrev: ()-> Unit) {
    val categories by viewModel.categories.observeAsState(emptyList())

    var newCategoryName by rememberSaveable { mutableStateOf("") }

    LazyColumn(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .padding(bottom = 8.dp)
            ) {

                Text("Add Category", style = MaterialTheme.typography.titleLarge)

                OutlinedTextField(
                    value = newCategoryName,
                    onValueChange = { newCategoryName = it },
                    label = { Text("Category name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(10.dp))
                Button(
                    onClick = {
                        if (newCategoryName.isNotBlank()) {
                            viewModel.addCategory(newCategoryName)
                            newCategoryName = ""
                        } },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Add Category")
                }
            }
        }
        items(categories) { category ->
            CategoryItem(
                modifier = Modifier.fillMaxWidth(),
                category = category,
                onDelete = { viewModel.deleteCategory(category) }
            )
        }
    }
}

@Composable
fun CategoryItem(
    modifier: Modifier,
    category: Category,
    onDelete: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors()
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    category.categoryName,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
            }

            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete"
                )
            }
        }
    }

}