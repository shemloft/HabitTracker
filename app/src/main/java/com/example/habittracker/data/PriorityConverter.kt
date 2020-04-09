package com.example.habittracker.data

import androidx.room.TypeConverter

class PriorityConverter {
    @TypeConverter
    fun fromPriority(value: Priority): Int = value.ordinal

    @TypeConverter
    fun toPriority(value: Int): Priority = Priority.values()[value]
}