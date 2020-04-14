package com.example.habittracker.viewmodel

import androidx.lifecycle.ViewModel
import com.example.habittracker.data.Habit
import com.example.habittracker.model.Model
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class EditorViewModel : ViewModel(), CoroutineScope {
    private val job = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + CoroutineExceptionHandler { _, e -> throw e }

    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancelChildren()
    }

    fun onFormFilled(habit: Habit, oldHabit: Habit?) = launch {
        if (oldHabit == null)
            withContext(Dispatchers.IO) { Model.addHabit(habit) }
        else
            withContext(Dispatchers.IO) { Model.replaceHabit(oldHabit, habit) }
    }
}