package com.example.lab2.repository

import com.example.lab2.model.AppearanceOption

class AppearanceRepository {
    fun getAppearanceOptions(): List<AppearanceOption> {
        return listOf(
            AppearanceOption("theme", "Theme", "Light / Dark / System"),
            AppearanceOption("font", "Font Size", "Small, medium, large"),
            AppearanceOption("language", "Language", "App language settings"),
            AppearanceOption("color", "Color Scheme", "Accent and palette"),
        )
    }
}