package com.example.lab2.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab2.model.User
import com.example.lab2.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val repository = UserRepository()

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _statusText = MutableStateFlow("Welcome!")
    val statusText = _statusText.asStateFlow()

    fun fetchUser() {
        viewModelScope.launch {
            _user.postValue(repository.getUser())
        }
    }

    fun changeStatus() {
        _statusText.value = "Profile updated!"
    }
}
