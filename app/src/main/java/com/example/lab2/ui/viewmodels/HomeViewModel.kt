package com.example.lab2.ui.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.random.Random

class HomeViewModel : ViewModel() {

    private val _mainText = MutableStateFlow("Welcome to Home Screen")
    val mainText = _mainText.asStateFlow()

    private val _infoVisible = MutableStateFlow(false)
    val infoVisible = _infoVisible.asStateFlow()

    private val _infoText = MutableStateFlow("This is a simple Compose app.\nVersion 1.0.0")
    val infoText = _infoText.asStateFlow()

    private val phrases = listOf(
        "Have a great day!",
        "Compose is fun!",
        "Kotlin rocks!",
        "Random phrase here",
        "You clicked the button!"
    )

    fun changeMainText() {
        val randomIndex = Random.nextInt(phrases.size)
        _mainText.value = phrases[randomIndex]
    }

    fun toggleInfo() {
        _infoVisible.value = !_infoVisible.value
    }
}
