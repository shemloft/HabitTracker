package com.example.habittracker.viewmodel

import androidx.lifecycle.ViewModel
import com.example.habittracker.data.Habit
import com.example.habittracker.model.Model

class EditorViewModel: ViewModel() {

    fun onFormFilled(habit: Habit, oldHabit: Habit?) {
        if (oldHabit == null)
            Model.addHabit(habit)
        else
            Model.replaceHabit(oldHabit, habit)
    }

}