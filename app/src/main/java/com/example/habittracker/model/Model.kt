package com.example.habittracker.model

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

    fun getAllHabits(): List<Habit> =
        database.habitsDao().getAllHabits()


    fun addHabit(habit: Habit) {
        habit.date = Date().time
        database.habitsDao().insertHabit(habit)
    }

    fun replaceAllHabits(habits: List<Habit>) {
        database.habitsDao().deleteAllHabits()
        database.habitsDao().insertHabits(habits)
    }

    fun replaceHabit(oldHabit: Habit, newHabit: Habit) {
        newHabit.id = oldHabit.id
        newHabit.uid = oldHabit.uid
        newHabit.date = Date().time
        updateHabit(newHabit)
    }

    fun updateHabit(habit: Habit) {
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