package com.example.habittracker.data

import android.os.Parcelable
import java.io.Serializable

data class Habit(
    val name: String,
    val description: String,
    val priority: Priority,
    val habitType: HabitType,
    val habitCount: Int,
    val habitFrequency: Int
) : Serializable {
}

