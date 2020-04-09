package com.example.habittracker.model

import androidx.lifecycle.LiveData
import com.example.habittracker.data.Habit
import com.example.habittracker.data.HabitType
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

    fun getHabits(habitType: HabitType): LiveData<List<Habit>> =
            database.habitsDao().getHabitsByType(habitType)
}