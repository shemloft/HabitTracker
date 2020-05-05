package com.example.habittracker.model

import android.util.Log
import com.example.habittracker.cloud.CloudRepository
import com.example.habittracker.data.Habit
import com.example.habittracker.data.HabitType
import com.example.habittracker.data.SortStatus
import com.example.habittracker.db.HabitsDatabase
import java.util.*

object Model {
    private lateinit var database: HabitsDatabase

    fun addDatabase(database: HabitsDatabase) {
        this.database = database
    }

    suspend fun updateDatabaseFromServer() {
        val habits = CloudRepository.getHabits()
        database.habitsDao().updateHabits(habits)
    }

    suspend fun addHabit(habit: Habit) {
        habit.date = Date().time
        val uid = CloudRepository.addHabit(habit)
        habit.uid = uid
        database.habitsDao().insertHabit(habit)
    }

    suspend fun replaceHabit(oldHabit: Habit, newHabit: Habit) {
        newHabit.uid = oldHabit.uid
        newHabit.date = Date().time
        CloudRepository.updateHabit(newHabit)
        updateHabit(newHabit)
    }

    private fun updateHabit(habit: Habit) {
        database.habitsDao().updateHabit(habit)
    }

    fun getSelectedHabits(habitType: HabitType, sortStatus: SortStatus, textFilter: String) =
        when (sortStatus) {
            SortStatus.UP -> database.habitsDao().getSelectedHabitsOrderedByAscending(
                habitType,
                textFilter
            )
            SortStatus.DOWN -> database.habitsDao().getSelectedHabitsOrderedByDescending(
                habitType,
                textFilter
            )
            SortStatus.NONE -> database.habitsDao().getSelectedHabitsUnordered(
                habitType,
                textFilter
            )
        }


}