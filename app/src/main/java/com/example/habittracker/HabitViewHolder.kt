package com.example.habittracker

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.habittracker.data.Habit
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.habit_layout.*

class HabitViewHolder(
    override val containerView: View,
    private val itemClick: ((Habit, Int) -> Unit)
) :
    RecyclerView.ViewHolder(containerView),
    LayoutContainer {

    fun bind(habit: Habit, position: Int) {
        habitName.text = habit.name
        habitDescription.text = habit.description
        habitRepetition.text = containerView.context.getString(
            R.string.habitRepetition,
            containerView.context.resources.getQuantityString(
                R.plurals.timesPlurals,
                habit.habitCount,
                habit.habitCount
            ),
            containerView.context.resources.getQuantityString(
                R.plurals.daysPlurals,
                habit.habitFrequency,
                habit.habitFrequency
            )
        )
        habitType.setImageResource(habit.habitType.iconResource)
        habitPriority.setImageResource(habit.priority.iconResource)

        habitLayout.setOnClickListener {
            itemClick(habit, position)
        }
    }
}