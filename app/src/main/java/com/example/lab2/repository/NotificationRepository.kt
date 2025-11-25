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
            Notification(6, "Survey", "Please take a moment to complete our survey."),
            Notification(7, "Discount Offer", "Get 20% off your next purchase!"),
            Notification(8, "New Feature", "Explore the new dark mode in settings."),
            Notification(9, "Maintenance", "Scheduled maintenance on Friday at 2 AM."),
            Notification(10, "Alert", "Unusual login activity detected."),
            Notification(11, "Friend Request", "You have a new friend request."),
            Notification(12, "Weekly Summary", "Check your activity summary for this week."),
            Notification(13, "Achievement", "You reached a new level!"),
            Notification(14, "Event Reminder", "Your booked event starts in 1 h")
        )
    }
}