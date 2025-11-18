package com.example.lab2.repository

import com.example.lab2.model.Notification

class NotificationRepository {

    fun getNotifications(): List<Notification> {
        return listOf(
            Notification(1, "Welcome!", "Thanks for installing our app!"),
            Notification(2, "Update Available", "Version 1.1.0 is now available."),
            Notification(3, "Event Today", "Don't forget the special event at 6 PM."),
            Notification(4, "Reminder", "Your subscription renews tomorrow."),
            Notification(5, "Tips", "Check out our new tips section."),
        )
    }
}