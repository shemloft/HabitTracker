package com.example.habittracker.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class Habit(
    val name: String,
    val description: String,
    val priority: Priority,
    val habitType: HabitType,
    val habitCount: Int,
    val habitFrequency: Int
) : Serializable, Parcelable

