package com.peter.eisenhowermatrix.ui.add_task

import android.app.Application
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.peter.eisenhowermatrix.R
import com.peter.eisenhowermatrix.pojo.Task
import com.peter.eisenhowermatrix.pojo.TaskType
import com.peter.eisenhowermatrix.service.AuthAppRepository
import com.peter.eisenhowermatrix.service.TaskFirebaseService
import kotlinx.coroutines.runBlocking

class AddTaskViewModel(application: Application) : AndroidViewModel(application) {

    private val _type = MutableLiveData<TaskType>()
    val type: LiveData<TaskType>
        get() = _type

    private val _message = MutableLiveData<String>()
    val message: LiveData<String>
        get() = _message

    var title: String = ""
    var description: String = ""

    private val _navToTask = MutableLiveData<Task>()
    val navToTask: LiveData<Task>
        get() = _navToTask

    private val authAppRepository: AuthAppRepository
    val userLiveData: MutableLiveData<FirebaseUser?>

    init {
        authAppRepository = AuthAppRepository(application)
        userLiveData = authAppRepository.userLiveData
        _type.value = TaskType.UNDEFINED
    }

    fun onType1Clicked() {
        _type.value = TaskType.IMPORTANT_URGENT
    }

    fun onType2Clicked() {
        _type.value = TaskType.IMPORTANT_NOT_URGENT
    }

    fun onType3Clicked() {
        _type.value = TaskType.NOT_IMPORTANT_URGENT
    }

    fun onType4Clicked() {
        _type.value = TaskType.NOT_IMPORTANT_NOT_URGENT
    }

    fun onTypeUndefinedClicked() {
        _type.value = TaskType.UNDEFINED
    }

    fun onAddClicked() {
        var task = Task()
        task.type = type.value!!
        task.title = title
        task.description = description
        task.userUid = userLiveData.value!!.uid!!
        task.isDone = false
        if (task.add() == TaskFirebaseService.ProcessState.SUCCESS
        ) {
            _message.value = "Added Successfully"
            _navToTask.value = task
        }
    }

    fun onNavToTaskCompleted() {
        _navToTask.value = null
    }

    fun onMessageDisplayCompleted(){
        _message.value = null
    }


}