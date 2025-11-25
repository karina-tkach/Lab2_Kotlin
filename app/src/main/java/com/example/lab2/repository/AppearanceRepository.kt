package com.example.lab2.repository

import com.example.lab2.model.AppearanceOption

class AppearanceRepository {
    fun getAppearanceOptions(): List<AppearanceOption> {
        return listOf(
            AppearanceOption("theme", "Theme", "Light / Dark / System"),
            AppearanceOption("font", "Font Size", "Small, medium, large"),
            AppearanceOption("language", "Language", "App language settings"),
            AppearanceOption("color", "Color Scheme", "Accent and palette"),
            AppearanceOption("icons", "Icon Style", "Rounded / Square / Outline"),
            AppearanceOption("animations", "Animations", "On / Off"),
            AppearanceOption("layout", "Layout", "Compact / Comfortable / Spacious"),
            AppearanceOption("background", "Background", "Wallpaper or pattern"),
            AppearanceOption("contrast", "High Contrast Mode", "On / Off"),
            AppearanceOption("accessibility", "Accessibility Options", "VoiceOver, Magnification")
        )
    }
}