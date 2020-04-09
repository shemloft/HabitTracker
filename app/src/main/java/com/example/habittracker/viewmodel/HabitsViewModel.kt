package com.example.habittracker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.habittracker.data.Habit
import com.example.habittracker.data.HabitType
import com.example.habittracker.data.SortStatus
import com.example.habittracker.model.Model

class HabitsViewModel : ViewModel() {
    private val goodHabitsMutable: MutableLiveData<List<Habit>> = MutableLiveData()
    private val badHabitsMutable: MutableLiveData<List<Habit>> = MutableLiveData()
    private val sortStatusMutable: MutableLiveData<SortStatus> = MutableLiveData()

    val goodHabits: LiveData<List<Habit>> = goodHabitsMutable
    val badHabits: LiveData<List<Habit>> = badHabitsMutable
    val sortStatus: LiveData<SortStatus> = sortStatusMutable

    init {
        goodHabitsMutable.value = Model.getImmutableHabits(HabitType.Good)
        badHabitsMutable.value = Model.getImmutableHabits(HabitType.Bad)
    }

    fun onSortStatusChange(sortStatus: SortStatus) {
        sortStatusMutable.value = sortStatus
    }

    fun onTextChanged(newText: String) {

    }

    fun getSortedHabits(habitType: HabitType, sortStatus: SortStatus): List<Habit> {
        val habits = Model.getImmutableHabits(habitType)
        return when (sortStatus) {
            SortStatus.NONE -> habits
            SortStatus.UP -> habits.sortedBy { habit -> habit.priority }
            SortStatus.DOWN -> habits.sortedByDescending { habit -> habit.priority }
        }
    }

    fun getPosition(relativePosition: Int, habitType: HabitType): Int {
        return when (sortStatus.value) {
            SortStatus.NONE -> relativePosition
            SortStatus.UP -> getActualPosition(relativePosition, habitType, false)
            SortStatus.DOWN -> getActualPosition(relativePosition, habitType, true)
            else -> relativePosition
        }
    }

    private fun getActualPosition(
        relativePosition: Int,
        habitType: HabitType,
        descending: Boolean
    ) : Int {
        val habitsWithIndices = Model.getImmutableHabits(habitType)
            .mapIndexed { index, habit -> Pair(index, habit) }
        val sorted = if (!descending) {
            habitsWithIndices.sortedBy { pair: Pair<Int, Habit> -> pair.second.priority }
        } else {
            habitsWithIndices.sortedByDescending { pair: Pair<Int, Habit> -> pair.second.priority }
        }
        return sorted[relativePosition].first
    }

}