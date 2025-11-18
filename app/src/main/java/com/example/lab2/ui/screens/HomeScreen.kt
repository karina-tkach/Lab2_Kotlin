package com.example.lab2.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lab2.ui.viewmodels.HomeViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel()
) {
    val mainText = viewModel.mainText.collectAsState()
    val infoVisible = viewModel.infoVisible.collectAsState()

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = mainText.value,
            modifier = Modifier.padding(16.dp)
        )

        Spacer(modifier = Modifier.padding(16.dp))

        Button(onClick = { viewModel.changeMainText() }) {
            Text("Change Text")
        }

        Spacer(modifier = Modifier.padding(8.dp))

        OutlinedButton(onClick = { viewModel.toggleInfo() }) {
            Text(if (infoVisible.value) "Hide Info" else "Show Info")
        }

        if (infoVisible.value) {
            Spacer(modifier = Modifier.padding(16.dp))
            Text(viewModel.infoText.value, modifier = Modifier.padding(8.dp))
        }
    }
}
