package com.example.habittracker.model

import com.example.habittracker.data.Habit
import com.example.habittracker.data.HabitType

object Model {
    private val goodHabits = arrayListOf<Habit>()
    private val badHabits = arrayListOf<Habit>()

    fun addHabit(habit: Habit) {
        getHabits(habit.habitType).add(habit)
    }

    fun replaceHabit(position: Int, oldHabit: Habit, newHabit: Habit) {
        if (oldHabit.habitType != newHabit.habitType) {
            getHabits(oldHabit.habitType).removeAt(position)
            addHabit(newHabit)
        } else {
            getHabits(newHabit.habitType)[position] = newHabit
        }
    }

    fun getImmutableHabits(habitType: HabitType): List<Habit> = getHabits(habitType)

//    fun getSortedByPriorityHabits(habitType: HabitType, descending: Boolean = true): List<Habit> {
//        return getHabits(habitType).sortBy { habit -> habit.habitType }
//    }

    private fun getHabits(habitType: HabitType) = when (habitType) {
        HabitType.Good -> goodHabits
        HabitType.Bad -> badHabits
    }
}