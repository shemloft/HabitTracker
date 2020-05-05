package com.example.habittracker.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.habittracker.data.*

@Dao
interface HabitsDao {
    @Query("SELECT * FROM habit")
    fun getAllHabits(): List<Habit>

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

    @Query("DELETE FROM habit")
    fun deleteAllHabits()

    @Delete
    fun deleteHabit(habit: Habit)

    @Insert
    fun insertHabit(habit: Habit): Long

    @Insert
    fun insertHabits(habits: List<Habit>)

    @Update
    fun updateHabit(habit: Habit)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateHabits(habits: List<Habit>)

}