package com.example.habittracker.viewmodel

import androidx.lifecycle.ViewModel
import com.example.habittracker.data.Habit
import com.example.habittracker.model.Model

class EditorViewModel: ViewModel() {

    fun onFormFilled(habit: Habit, position: Int?, oldHabit: Habit?) {
        if (position == null) {
            Model.addHabit(habit)
        } else {
            if (oldHabit == null)
                throw IllegalArgumentException("there should be old habit")
            Model.replaceHabit(position, oldHabit, habit)
        }
    }

}