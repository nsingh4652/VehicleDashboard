package com.example.vehicledashboard.presentation.util

fun formatLastUpdated(timestampMillis: Long): String {
    val difference =
        System.currentTimeMillis() - timestampMillis

    val seconds = difference / 1000
    val minutes = seconds / 60
    val hours = minutes / 60

    return when {
        seconds < 60 -> "Just now"

        minutes == 1L -> "1 minute ago"

        minutes < 60 -> "$minutes minutes ago"

        hours == 1L -> "1 hour ago"

        hours < 24 -> "$hours hours ago"

        else -> "More than a day ago"
    }
}