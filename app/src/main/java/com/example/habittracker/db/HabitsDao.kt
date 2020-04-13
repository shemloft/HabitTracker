package com.example.habittracker.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.habittracker.data.*

@Dao
interface HabitsDao {
    @Query("SELECT * FROM habit WHERE habitType == :habitType AND name LIKE '%' || :textFilter || '%'")
    fun getSelectedHabitsUnordered(
        @TypeConverters(HabitTypeConverter::class) habitType: HabitType,
        textFilter: String
    ): LiveData<List<Habit>>

    @Query("SELECT * FROM habit WHERE habitType == :habitType AND name LIKE '%' || :textFilter || '%' ORDER BY priority ASC")
    fun getSelectedHabitsOrderedByAscending(
        @TypeConverters(HabitTypeConverter::class) habitType: HabitType,
        textFilter: String
    ): LiveData<List<Habit>>

    @Query("SELECT * FROM habit WHERE habitType == :habitType AND name LIKE '%' || :textFilter || '%' ORDER BY priority DESC")
    fun getSelectedHabitsOrderedByDescending(
        @TypeConverters(HabitTypeConverter::class) habitType: HabitType,
        textFilter: String
    ): LiveData<List<Habit>>

    @Insert
    fun insertHabit(habit: Habit)

    @Update
    fun updateHabit(habit: Habit)

}