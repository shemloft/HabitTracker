package com.example.habittracker.model

import com.example.habittracker.data.Habit
import com.example.habittracker.data.HabitType

object Model {
    private val habits = arrayListOf<Habit>()

    fun getHabits() = habits

    fun addHabit(habit: Habit) {
        habits.add(habit)
    }

    fun replaceHabit(relativePosition: Int, oldHabit: Habit, newHabit: Habit) {
        val actualPosition = getHabitsIndexes(oldHabit.habitType)[relativePosition]
        habits[actualPosition] = newHabit
    }

    private fun getHabitsIndexes(habitType: HabitType) =
        habits.mapIndexed { index, habit -> if (habit.habitType == habitType) index else null }
            .filterNotNull()

    fun getHabits(habitType: HabitType) = habits.filter { habit -> habit.habitType == habitType }
}