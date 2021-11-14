package com.peter.eisenhowermatrix.pojo

import android.app.Application
import android.os.Parcelable
import com.peter.eisenhowermatrix.service.TaskFirebaseService
import kotlinx.android.parcel.Parcelize

@Parcelize
class Task : Parcelable {
    var title: String = ""
    var description: String = ""
    var userUid: String = ""
    var id: String = ""
    var type: TaskType = TaskType.UNDEFINED
    var isDone : Boolean = false
    fun add(): TaskFirebaseService.ProcessState {
        return try {
            TaskFirebaseService().setTask(TaskFirebaseService().initTask(userUid),this)
            TaskFirebaseService.ProcessState.SUCCESS
        } catch (e: Exception) {
            TaskFirebaseService.ProcessState.FAILURE
        }
    }

    fun update() : TaskFirebaseService.ProcessState {
        return try {
            TaskFirebaseService().setTask(id,this)
            TaskFirebaseService.ProcessState.SUCCESS
        } catch (e: Exception) {
            TaskFirebaseService.ProcessState.FAILURE
        }
    }

    fun delete() : TaskFirebaseService.ProcessState{
        return try {
            TaskFirebaseService().deleteTask(this)
            TaskFirebaseService.ProcessState.SUCCESS
        } catch (e: Exception) {
            TaskFirebaseService.ProcessState.FAILURE
        }
    }
}


enum class TaskType(val value: String) {
    UNDEFINED("UNDEFINED"),
    IMPORTANT_URGENT("IMPORTANT_URGENT"),
    IMPORTANT_NOT_URGENT("IMPORTANT_NOT_URGENT"),
    NOT_IMPORTANT_URGENT("NOT_IMPORTANT_URGENT"),
    NOT_IMPORTANT_NOT_URGENT("NOT_IMPORTANT_NOT_URGENT")
}