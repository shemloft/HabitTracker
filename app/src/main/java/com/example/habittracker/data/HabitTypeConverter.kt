package com.example.habittracker.data

import androidx.room.TypeConverter

class HabitTypeConverter {
    @TypeConverter
    fun fromHabitType(value: HabitType) = value.ordinal

    @TypeConverter
    fun toHabitType(value: Int) = HabitType.values()[value]
}