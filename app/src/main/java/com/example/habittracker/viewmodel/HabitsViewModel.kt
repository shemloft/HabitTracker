package com.example.habittracker.viewmodel

import androidx.lifecycle.*
import com.example.habittracker.data.Habit
import com.example.habittracker.data.HabitSelectionParameters
import com.example.habittracker.data.HabitType
import com.example.habittracker.data.SortStatus
import com.example.habittracker.model.Model
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HabitsViewModel : ViewModel() {
    private val habitSelectionParameters: MutableLiveData<HabitSelectionParameters> =
        MutableLiveData()

    val goodHabits: LiveData<List<Habit>> =
        Transformations.switchMap(habitSelectionParameters) { p ->
            Model.getSelectedHabits(HabitType.Good, p.sortStatus, p.textFilter)
        }
    val badHabits: LiveData<List<Habit>> =
        Transformations.switchMap(habitSelectionParameters) { p ->
            Model.getSelectedHabits(HabitType.Bad, p.sortStatus, p.textFilter)
        }

    init {
        habitSelectionParameters.value = HabitSelectionParameters(SortStatus.NONE, "")

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                Model.updateDatabaseFromServer()
            }
        }
    }

    fun onSortStatusChange(sortStatus: SortStatus) {
        habitSelectionParameters.value =
            HabitSelectionParameters(sortStatus, habitSelectionParameters.value?.textFilter ?: "")
    }

    fun onTextChanged(newText: String) {
        habitSelectionParameters.value =
            HabitSelectionParameters(
                habitSelectionParameters.value?.sortStatus ?: SortStatus.NONE,
                newText
            )
    }
}