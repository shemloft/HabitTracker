package com.example.habittracker.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
@Entity
@TypeConverters(PriorityConverter::class, HabitTypeConverter::class)
data class Habit(
    val name: String,
    val description: String,
    val priority: Priority,
    val habitType: HabitType,
    val habitCount: Int,
    val habitFrequency: Int
) : Serializable, Parcelable {
    @PrimaryKey
    @IgnoredOnParcel
    var uid: String = ""
    @IgnoredOnParcel
    var date: Long? = null
}

