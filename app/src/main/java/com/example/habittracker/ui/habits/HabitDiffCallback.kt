package com.example.habittracker.ui.habits

import androidx.recyclerview.widget.DiffUtil
import com.example.habittracker.data.Habit

class HabitDiffCallback(private val habits: List<Habit>, private val newHabits: List<Habit>) :
    DiffUtil.Callback() {

    override fun getOldListSize() = habits.size

    override fun getNewListSize() = newHabits.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        areContentsTheSame(oldItemPosition, newItemPosition)

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        habits[oldItemPosition] == newHabits[newItemPosition]
}
