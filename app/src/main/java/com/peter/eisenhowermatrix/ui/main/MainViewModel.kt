package com.peter.eisenhowermatrix.ui.main

import androidx.lifecycle.MutableLiveData

import com.google.firebase.auth.FirebaseUser

import com.peter.eisenhowermatrix.service.AuthAppRepository

import android.app.Application
import android.util.Log

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.peter.eisenhowermatrix.pojo.TaskType
import com.peter.eisenhowermatrix.service.TaskFirebaseService
import kotlinx.coroutines.*


class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val authAppRepository: AuthAppRepository
    val userLiveData: MutableLiveData<FirebaseUser?>

    private val _navToAdd = MutableLiveData<Boolean>()
    val navToAdd: LiveData<Boolean>
        get() = _navToAdd

    private val _selectedType = MutableLiveData<TaskType>()
    val selectedType: LiveData<TaskType>
        get() = _selectedType

    private val _counter1 = MutableLiveData<String>()
    val counter1: LiveData<String>
        get() = _counter1

    private val _counter2 = MutableLiveData<String>()
    val counter2: LiveData<String>
        get() = _counter2

    private val _counter3 = MutableLiveData<String>()
    val counter3: LiveData<String>
        get() = _counter3

    private val _counter4 = MutableLiveData<String>()
    val counter4: LiveData<String>
        get() = _counter4

    private val _counterUndefined = MutableLiveData<String>()
    val counterUndefined: LiveData<String>
        get() = _counterUndefined

    val loggedOutLiveData: MutableLiveData<Boolean>

    fun logOut() {
        authAppRepository.logOut()
    }

    init {
        authAppRepository = AuthAppRepository(application)
        userLiveData = authAppRepository.userLiveData
        loggedOutLiveData = authAppRepository.loggedOutLiveData
        val newScope = CoroutineScope(Dispatchers.Default).launch {
            val result = fetch()
            if (result) {
                Log.i("DONE", "fetched")
            } else {
                Log.e("ERROR", "Can't fetch")
            }
        }//        _counter1.value = TaskFirebaseService().getCounter1(userLiveData.value!!.uid)
//  _counter1.value = TaskFirebaseService().count1.value


        _selectedType.value = null
    }

    fun onAddClicked() {
        _navToAdd.value = true;
    }

    fun onAddClickedCompleted() {
        _navToAdd.value = false;
    }

    suspend fun fetch() = withContext(Dispatchers.IO) {
        try {
            listOf(
                launch { TaskFirebaseService().counter1(userLiveData.value!!.uid, _counter1) },
                launch { TaskFirebaseService().counter2(userLiveData.value!!.uid, _counter2) },
                launch { TaskFirebaseService().counter3(userLiveData.value!!.uid, _counter3) },
                launch { TaskFirebaseService().counter4(userLiveData.value!!.uid, _counter4) },
                launch {
                    TaskFirebaseService().counterUndefined(
                        userLiveData.value!!.uid,
                        _counterUndefined
                    )
                },
            ).joinAll()
            true
        } catch (e: Throwable) {
            false
        }
    }

    fun onType1Clicked() {
        _selectedType.value = TaskType.IMPORTANT_URGENT
    }

    fun onType2Clicked() {
        _selectedType.value = TaskType.IMPORTANT_NOT_URGENT

    }

    fun onType3Clicked() {
        _selectedType.value = TaskType.NOT_IMPORTANT_URGENT
    }

    fun onType4Clicked() {
        _selectedType.value = TaskType.NOT_IMPORTANT_NOT_URGENT
    }

    fun onTypeUndefinedClicked() {
        _selectedType.value = TaskType.UNDEFINED
    }

    fun onTypeNavigationCompleted() {
        _selectedType.value = null
    }

    fun onLogout() {
        authAppRepository.logOut()
    }
}