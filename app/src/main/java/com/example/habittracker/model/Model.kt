package com.example.habittracker.model

import com.example.habittracker.data.Habit
import com.example.habittracker.data.HabitType
import com.example.habittracker.db.HabitsDatabase

object Model {
    private val goodHabits = arrayListOf<Habit>()
    private val badHabits = arrayListOf<Habit>()

    private lateinit var database: HabitsDatabase

    fun addDatabase(database: HabitsDatabase) {
        this.database = database
    }

    fun addHabit(habit: Habit) {
        database.habitsDao().insertHabit(habit)
    }

    fun replaceHabit(position: Int, oldHabit: Habit, newHabit: Habit) {
        newHabit.id = oldHabit.id
        database.habitsDao().updateHabit(newHabit)
    }

    fun getImmutableHabits(habitType: HabitType): List<Habit> = getHabits(habitType)

    private fun getHabits(habitType: HabitType) = database.habitsDao().getHabitsByType(habitType)
}