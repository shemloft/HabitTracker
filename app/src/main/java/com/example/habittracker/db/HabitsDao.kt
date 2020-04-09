package com.example.habittracker.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.habittracker.data.Habit
import com.example.habittracker.data.HabitType
import com.example.habittracker.data.HabitTypeConverter

@Dao
interface HabitsDao {
    @Query("SELECT * FROM habit WHERE habitType == :habitType")
    fun getHabitsByType(@TypeConverters(HabitTypeConverter::class) habitType: HabitType): LiveData<List<Habit>>

    @Insert
    fun insertHabit(habit: Habit)

    @Update
    fun updateHabit(habit: Habit)

}