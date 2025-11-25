package com.example.lab2.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lab2.model.Notification
import com.example.lab2.ui.viewmodels.NotificationsViewModel

@Composable
fun NotificationsScreen(
    modifier: Modifier = Modifier,
    viewModel: NotificationsViewModel = viewModel()
) {
    val notifications by viewModel.notifications.observeAsState(emptyList())
    val showFull by viewModel.showFullList.observeAsState(false)
    SideEffect {
        viewModel.fetchNotifications()
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text("Notifications", modifier = Modifier.padding(bottom = 16.dp))

        /*OutlinedButton(onClick = { viewModel.fetchNotifications() }) {
            Text("Reload List")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { viewModel.toggleFullList() }) {
            Text(if (showFull) "Hide Full List" else "Show Full List")
        }*/
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
                    onClick = { viewModel.fetchNotifications() },
                    modifier = Modifier.weight(1f)
                ) { Text("Reload List") }

                Button(
                    onClick = { viewModel.toggleFullList() },
                    modifier = Modifier.weight(1f)
                ) { Text(if (showFull) "Hide Full List" else "Show Full List") }
            }
        } else {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(onClick = { viewModel.fetchNotifications() }, modifier = Modifier.fillMaxWidth()) {
                    Text("Reload List")
                }

                Button(onClick = { viewModel.toggleFullList() }, modifier = Modifier.fillMaxWidth()) {
                    Text(if (showFull) "Hide Full List" else "Show Full List")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (notifications.isEmpty()) {
            Text("No notifications available.", modifier = Modifier.padding(16.dp))
        } else {
            val listToShow = if (showFull) notifications else notifications.take(3)

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(listToShow) { notification ->
                    NotificationItem(notification)
                }
            }
        }
    }
}

@Composable
fun NotificationItem(notification: Notification) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(notification.title, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                notification.body,
                maxLines = 1
            )
        }
    }
}
