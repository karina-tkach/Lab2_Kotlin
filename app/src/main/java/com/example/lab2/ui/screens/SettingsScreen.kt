package com.example.lab2.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lab2.model.AppearanceOption
import com.example.lab2.ui.viewmodels.AppearanceOptionsViewModel


sealed class SettingsScreenRoute(val route: String) {
    object Main : SettingsScreenRoute("settings_main")
    object AppearanceOptionsScreen : SettingsScreenRoute("settings_appearance")
    object AdvancedOptionsScreen : SettingsScreenRoute("settings_advanced")
}

@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = SettingsScreenRoute.Main.route,
        modifier = modifier
    ) {

        composable(SettingsScreenRoute.Main.route) {
            SettingsMainScreen(
                onNext = { navController.navigate(SettingsScreenRoute.AppearanceOptionsScreen.route) }
            )
        }

        composable(SettingsScreenRoute.AppearanceOptionsScreen.route) {
            SettingsAppearanceOptionsScreen(
                onNext = { navController.navigate(SettingsScreenRoute.AdvancedOptionsScreen.route) },
                onPrev = {navController.popBackStack()}
            )
        }

        composable(SettingsScreenRoute.AdvancedOptionsScreen.route) {
            SettingsAdvancedOptionsScreen(
                onPrev = {navController.popBackStack()}
            )
        }
    }
}

@Composable
fun SettingsMainScreen(onNext: () -> Unit) {

    var labelText by rememberSaveable { mutableStateOf("Settings screen") }
    var toggleState by rememberSaveable { mutableStateOf(false) }
    var sliderValue by remember { mutableFloatStateOf(0.5f) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Bottom
    ) {

        Text(text = labelText, fontSize = 22.sp)

        Spacer(Modifier.height(16.dp))

        Button(onClick = onNext) {
            Text("View set appearance options")
        }

        Spacer(Modifier.height(20.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Enable option")
            Spacer(Modifier.width(8.dp))
            Switch(
                checked = toggleState,
                onCheckedChange = { toggleState = it }
            )
        }

        Spacer(Modifier.height(16.dp))

        Column(horizontalAlignment = Alignment.End) {
            Text("Volume: ${(sliderValue * 100).toInt()}%")
            Slider(
                value = sliderValue,
                onValueChange = { sliderValue = it },
                modifier = Modifier.width(200.dp)
            )
        }

        Spacer(Modifier.height(24.dp))

        Button(onClick = { labelText = "Settings updated!" }) {
            Text("Update settings")
        }
    }
}

@Composable
fun SettingsAppearanceOptionsScreen(onNext: () -> Unit, onPrev: () -> Unit,
                                    viewModel: AppearanceOptionsViewModel = viewModel()) {

    val appearanceOptions by viewModel.appearanceOptions.observeAsState(emptyList())
    val showFull by viewModel.showFullList.observeAsState(false)
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text("Options", modifier = Modifier.padding(bottom = 16.dp))

        Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround) {
            Button(onClick = onPrev) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                Text("Back")
            }
            Button(onClick = onNext) {
                Icon(Icons.AutoMirrored.Filled.ArrowForward, "")
                Text("To Advanced")
            }
        }
        

        Spacer(modifier = Modifier.height(8.dp))

        val configuration = LocalConfiguration.current
        val isLandscape = configuration.screenWidthDp > configuration.screenHeightDp

        if (isLandscape) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = { viewModel.fetchOptions() },
                    modifier = Modifier.weight(1f)
                ) { Text("Reload List") }

                Button(
                    onClick = { viewModel.toggleFullList() },
                    modifier = Modifier.weight(1f)
                ) { Text(if (showFull) "Hide Full List" else "Show Full List") }
            }
        } else {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = { viewModel.fetchOptions() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Reload List")
                }

                Button(
                    onClick = { viewModel.toggleFullList() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (showFull) "Hide Full List" else "Show Full List")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (appearanceOptions.isEmpty()) {
            Text("No options available.", modifier = Modifier.padding(16.dp))
        } else {
            val listToShow = if (showFull) appearanceOptions else appearanceOptions.take(3)

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(listToShow) { option ->
                    OptionItem(option)
                }
            }
        }
    }
}

@Composable
fun OptionItem(option: AppearanceOption) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(option.title, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                option.description,
                maxLines = 1
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsAdvancedOptionsScreen(
    onPrev: () -> Unit
) {
    val advancedOptions = listOf(
        AppearanceOption("ui_density", "UI Density", "Compact / Comfortable / Spacious"),
        AppearanceOption("corners", "Corner Radius", "Sharp / Medium / Rounded"),
        AppearanceOption("motion", "Motion Effects", "Enable or reduce transitions"),
        AppearanceOption("contrast", "High Contrast Mode", "Increase UI contrast"),
        AppearanceOption("blur", "Blur Effects", "Enable/disable blur backgrounds"),
        AppearanceOption("icons", "Icon Style", "Outline / Filled / Rounded"),
        AppearanceOption("cards", "Card Style", "Elevated / Filled / Outlined"),
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Advanced Appearance") },
                navigationIcon = {
                    OutlinedButton(
                        onClick = onPrev,
                        modifier = Modifier
                            .padding(start = 8.dp, end = 8.dp)
                            .size(40.dp),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->

        LazyVerticalGrid(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            items(advancedOptions) { option ->
                AdvancedOptionCard(option)
            }
        }
    }
}

@Composable
fun AdvancedOptionCard(option: AppearanceOption) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(option.title, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
            Text(option.description, style = MaterialTheme.typography.bodySmall)
        }
    }
}


