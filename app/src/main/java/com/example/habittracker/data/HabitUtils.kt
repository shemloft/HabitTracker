package com.example.habittracker.data

class HabitUtils {
    companion object {
        fun getRepetitionString(habit: Habit): String {
            val count = habit.habitCount
            val frequency = habit.habitFrequency
            return "$count ${getTimesForCount(count)} в $frequency ${getDaysForFrequency(frequency)}"
        }

        private fun getTimesForCount(count: Int): String {
            val lastNumber = count % 10
            val twoLastNumbers = count % 100
            return when {
                lastNumber !in 2..4 -> "раз"
                twoLastNumbers in 12..14 -> "раз"
                else -> "раза"
            }
        }

        private fun getDaysForFrequency(frequency: Int): String {
            val lastNumber = frequency % 10
            val twoLastNumbers = frequency % 100
            return when {
                lastNumber !in 1..4 -> "дней"
                twoLastNumbers in 11..14 -> "дней"
                lastNumber == 1 -> "день"
                else -> "дня"
            }
        }
    }
}