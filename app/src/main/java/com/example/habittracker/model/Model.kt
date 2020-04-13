package com.example.habittracker.model

import com.example.habittracker.data.Habit
import com.example.habittracker.data.HabitType
import com.example.habittracker.data.SortStatus
import com.example.habittracker.db.HabitsDatabase

object Model {
    private lateinit var database: HabitsDatabase

    fun addDatabase(database: HabitsDatabase) {
        this.database = database
    }

    fun addHabit(habit: Habit) {
        database.habitsDao().insertHabit(habit)
    }

    fun replaceHabit(oldHabit: Habit, newHabit: Habit) {
        newHabit.id = oldHabit.id
        database.habitsDao().updateHabit(newHabit)
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