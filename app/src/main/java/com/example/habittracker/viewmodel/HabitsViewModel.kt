package com.example.habittracker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.habittracker.data.Habit
import com.example.habittracker.data.HabitType
import com.example.habittracker.model.Model

class HabitsViewModel : ViewModel() {
    private val goodHabitsMutable: MutableLiveData<List<Habit>> = MutableLiveData()
    private val badHabitsMutable: MutableLiveData<List<Habit>> = MutableLiveData()

    val goodHabits: LiveData<List<Habit>> = goodHabitsMutable
    val badHabits: LiveData<List<Habit>> = badHabitsMutable

    init {
        goodHabitsMutable.value = Model.getImmutableHabits(HabitType.Good)
        badHabitsMutable.value = Model.getImmutableHabits(HabitType.Bad)
    }
}