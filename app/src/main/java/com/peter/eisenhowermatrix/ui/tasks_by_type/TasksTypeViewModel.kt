package com.peter.eisenhowermatrix.ui.tasks_by_type

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.peter.eisenhowermatrix.pojo.Task
import com.peter.eisenhowermatrix.pojo.TaskType
import com.peter.eisenhowermatrix.service.AuthAppRepository
import com.peter.eisenhowermatrix.service.TaskFirebaseService

class TasksTypeViewModel(val type: TaskType, app: Application) :
    AndroidViewModel(app) {

    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>>
        get() = _tasks
    private val authAppRepository: AuthAppRepository
    val userLiveData: MutableLiveData<FirebaseUser?>
    
    init {
        authAppRepository = AuthAppRepository(app)
        userLiveData = authAppRepository.userLiveData
        fetchTasks()
    }

    private fun fetchTasks() {
        TaskFirebaseService().fetchTasks(userLiveData.value!!.uid, type, _tasks)
    }
}