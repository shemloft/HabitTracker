package com.example.habittracker.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.habittracker.data.Habit

@Database(entities = [Habit::class], version = 1)
abstract class HabitsDatabase: RoomDatabase() {
    abstract fun habitsDao(): HabitsDao
}