package com.example.habittracker.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.habittracker.data.Habit
import com.example.habittracker.data.HabitType
import com.example.habittracker.data.SortStatus
import com.example.habittracker.model.Model

class HabitsViewModel : ViewModel() {
    private val sortStatusMutable: MutableLiveData<SortStatus> = MutableLiveData()
    private val filterTextMutable: MutableLiveData<String> = MutableLiveData()

    val goodHabits: LiveData<List<Habit>> = Model.getHabits(HabitType.Good)
    val badHabits: LiveData<List<Habit>> = Model.getHabits(HabitType.Bad)
    val sortStatus: LiveData<SortStatus> = sortStatusMutable
    val filterText: LiveData<String> = filterTextMutable

    init {
        sortStatusMutable.value = SortStatus.NONE
        filterTextMutable.value = ""
    }

    fun onSortStatusChange(sortStatus: SortStatus) {
        sortStatusMutable.value = sortStatus
    }

    fun onTextChanged(newText: String) {
        filterTextMutable.value = newText
    }

    fun getUpdatedHabits(habitType: HabitType): List<Habit> {
        val habits = when (habitType) {
            HabitType.Good -> goodHabits
            HabitType.Bad -> badHabits
        }
        return getFilteredHabits(
            getSortedHabits(
                habits.value ?: listOf(),
                sortStatus.value
            ), filterText.value
        )
    }

    private fun getSortedHabits(habits: List<Habit>, sortStatus: SortStatus?) =
        when (sortStatus) {
            SortStatus.UP -> habits.sortedBy { habit -> habit.priority }
            SortStatus.DOWN -> habits.sortedByDescending { habit -> habit.priority }
            else -> habits
        }

    private fun getFilteredHabits(habits: List<Habit>, text: String?) =
        habits.filter { habit -> habit.name.contains(text ?: "") }
}