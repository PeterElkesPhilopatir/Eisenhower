package com.peter.eisenhowermatrix.ui.task_profile

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.peter.eisenhowermatrix.pojo.Task

class TaskProfileViewModelFactory(
    private val task: Task,
    private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskProfileViewModel::class.java)) {
            return TaskProfileViewModel(task, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
