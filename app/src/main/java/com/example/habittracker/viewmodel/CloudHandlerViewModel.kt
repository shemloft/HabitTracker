package com.example.habittracker.viewmodel

import androidx.lifecycle.ViewModel
import com.example.habittracker.cloud.CloudRepository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class CloudHandlerViewModel : ViewModel(), CoroutineScope {

    private val job = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + CoroutineExceptionHandler { _, e -> throw e }

    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancelChildren()
    }

    fun onSaveClicked() = launch {
        withContext(Dispatchers.IO) { CloudRepository.saveDatabase() }
    }

    fun onLoadClicked() = launch {
        withContext(Dispatchers.IO) { CloudRepository.loadDatabase() }
    }
}