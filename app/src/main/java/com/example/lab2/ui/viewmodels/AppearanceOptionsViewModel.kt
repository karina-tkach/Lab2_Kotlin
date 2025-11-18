package com.example.lab2.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab2.model.AppearanceOption
import com.example.lab2.repository.AppearanceRepository
import kotlinx.coroutines.launch

class AppearanceOptionsViewModel : ViewModel() {
    private val repository = AppearanceRepository()
    private val _appearanceOptions = MutableLiveData<List<AppearanceOption>>(emptyList())
    val appearanceOptions: LiveData<List<AppearanceOption>> = _appearanceOptions
    private val _showFullList = MutableLiveData(false)
    val showFullList = _showFullList

    init {
        fetchOptions()
    }

    fun fetchOptions() {
        viewModelScope.launch {
            val list = repository.getAppearanceOptions()
            _appearanceOptions.postValue(list)
        }
    }

    fun toggleFullList() {
        viewModelScope.launch {
            _showFullList.postValue(!(_showFullList.value ?: false))
        }
    }
}