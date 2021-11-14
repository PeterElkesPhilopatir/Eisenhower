package com.peter.eisenhowermatrix.ui.task_profile

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.peter.eisenhowermatrix.pojo.Task
import com.peter.eisenhowermatrix.pojo.TaskType
import com.peter.eisenhowermatrix.service.AuthAppRepository
import com.peter.eisenhowermatrix.service.TaskFirebaseService

class TaskProfileViewModel(task: Task, app: Application) :
    AndroidViewModel(app) {
    var id = ""
    var title: String = ""
    var description: String = ""

    private val _isDone = MutableLiveData<Boolean>()
    val isDone: LiveData<Boolean>
        get() = _isDone

    private val _share = MutableLiveData<String>()
    val share: LiveData<String>
        get() = _share

    private val _back = MutableLiveData<Boolean>()
    val back: LiveData<Boolean>
        get() = _back

    private val _type = MutableLiveData<TaskType>()
    val type: LiveData<TaskType>
        get() = _type

    private val _message = MutableLiveData<String>()
    val message: LiveData<String>
        get() = _message

    private val authAppRepository: AuthAppRepository
    val userLiveData: MutableLiveData<FirebaseUser?>


    init {
        authAppRepository = AuthAppRepository(app)
        userLiveData = authAppRepository.userLiveData
        id = task.id
        title = task.title
        description = task.description
        _type.value = task.type
        _isDone.value = task.isDone
        _share.value = null
        _message.value = null
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

    fun onSaveClicked() {
        var task = Task()
        task.id = id
        task.type = _type.value!!
        task.title = title
        task.description = description
        task.userUid = userLiveData.value!!.uid!!
        task.isDone = _isDone.value!!
        if (task.update() == TaskFirebaseService.ProcessState.SUCCESS
        ) {
            _message.value = "Saved Successfully"
        }
    }

    fun onDeleteClicked() {
        var task = Task()
        task.id = id
        task.type = type.value!!
        task.title = title
        task.description = description
        task.userUid = userLiveData.value!!.uid!!
        task.isDone = _isDone.value!!
        if (task.delete() == TaskFirebaseService.ProcessState.SUCCESS
        ) {
            _back.value = true
        }
    }

    fun onDeleteClickedCompleted() {
        _back.value = false;
    }

    fun onCompletedClicked() {
        _isDone.value = true
        var task = Task()
        task.id = id
        task.type = type.value!!
        task.title = title
        task.description = description
        task.userUid = userLiveData.value!!.uid!!
        task.isDone = true

        if (task.update() == TaskFirebaseService.ProcessState.SUCCESS
        ) {
            _message.value = "Congrats"
        }
    }

    fun onMessageDisplayCompleted() {
        _message.value = null
    }

    fun onShareClicked(){
        if (_isDone.value!!)
            _share.value = "Mission Accomplished! \n I have done " + title
        else if (!isDone.value!!) _share.value = "Believe me ! I will do that " + title
        _share.value = ""
    }
    fun onShareClickedCompleted(){
        _share.value = null
    }

}