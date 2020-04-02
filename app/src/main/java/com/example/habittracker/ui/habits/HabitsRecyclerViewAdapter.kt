package com.example.habittracker.ui.habits

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.habittracker.R
import com.example.habittracker.data.Habit


class HabitsRecyclerViewAdapter(
    private val habits: ArrayList<Habit>,
    private val itemClick: ((Habit, Int) -> Unit)
) :
    RecyclerView.Adapter<HabitViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return HabitViewHolder(
            inflater.inflate(
                R.layout.habit_layout,
                parent,
                false
            ), itemClick
        )
    }

    override fun getItemCount(): Int = habits.size

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        holder.bind(habits[position], position)
    }
}

