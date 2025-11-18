package com.example.lab2.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab2.model.Notification
import com.example.lab2.repository.NotificationRepository
import kotlinx.coroutines.launch

class NotificationsViewModel : ViewModel() {

    private val repository = NotificationRepository()
    private val _notifications = MutableLiveData<List<Notification>>(emptyList())
    val notifications: LiveData<List<Notification>> = _notifications
    private val _showFullList = MutableLiveData(false)
    val showFullList = _showFullList

    /*init {
        fetchNotifications()
    }*/

    fun fetchNotifications() {
        viewModelScope.launch {
            val list = repository.getNotifications()
            _notifications.postValue(list)
        }
    }

    fun toggleFullList() {
        viewModelScope.launch {
            _showFullList.postValue(!(_showFullList.value ?: false))
        }
    }
}
