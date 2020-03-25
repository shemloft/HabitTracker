package com.example.habittracker

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.habittracker.data.Habit
import com.example.habittracker.data.HabitUtils
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.habit_layout.view.*

class HabitViewHolder(
    override val containerView: View,
    private val itemClick: ((Habit, Int) -> Unit)
) :
    RecyclerView.ViewHolder(containerView),
    LayoutContainer {
    private val habitName = containerView.habitName
    private val habitDescription = containerView.habitDescription
    private val habitRepetition = containerView.habitRepetition
    private val habitType = containerView.habitType
    private val habitPriority = containerView.habitPriority

    private val habitLayout = containerView.habitLayout

    fun bind(habit: Habit, position: Int) {
        habitName.text = habit.name
        habitDescription.text = habit.description
        habitRepetition.text = HabitUtils.getRepetitionString(habit)
        habitType.setImageResource(habit.habitType.iconResource())
        habitPriority.setImageResource(habit.priority.iconResource())

        habitLayout.setOnClickListener {
            itemClick(habit, position)
        }
    }
}