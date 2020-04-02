package com.example.habittracker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.habittracker.data.Habit
import com.example.habittracker.data.HabitType
import com.example.habittracker.model.Model

class HabitsViewModel : ViewModel() {
    private val mutableHabits: MutableLiveData<List<Habit>> = MutableLiveData()

    val habits: LiveData<List<Habit>> = mutableHabits

    init {
        mutableHabits.value = Model.getHabits()
    }

    fun onHabitTypeChanged(habitType: HabitType){
        mutableHabits.value = Model.getHabits(habitType)
    }
}