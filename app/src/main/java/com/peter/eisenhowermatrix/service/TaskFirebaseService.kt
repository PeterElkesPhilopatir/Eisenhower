package com.peter.eisenhowermatrix.service

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.peter.eisenhowermatrix.pojo.Task
import com.peter.eisenhowermatrix.pojo.TaskType
import kotlinx.coroutines.runBlocking

class TaskFirebaseService (){

//    private val authAppRepository: AuthAppRepository
//    val userLiveData: MutableLiveData<FirebaseUser?>


    private val reference = "tasks"
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val myRef: DatabaseReference = database.getReference(reference)
    var count1 = MutableLiveData<String>()
    
    fun initTask(user: String): String { // returns key only!!!
        var key = ""
        return try {
            runBlocking {
                key = myRef.child(user).push().key!!
            }
            key
        } catch (e: Exception) {
            Log.e("ERROR_ADD", e.message.toString())
            key
        }
    }

    fun setTask(key: String, task: Task): ProcessState {
        task.id = key
        return try {
            runBlocking {
                myRef.child(task.userUid).child(task.id).setValue(task)
            }
            ProcessState.SUCCESS
        } catch (e: Exception) {
            Log.e("ERROR_UPDATE", e.message.toString())
            ProcessState.FAILURE
        }
    }

    fun deleteTask(task: Task): ProcessState {
        return try {
            runBlocking {
                myRef.child(task.userUid).child(task.id).removeValue()
            }
            ProcessState.SUCCESS
        } catch (e: Exception) {
            Log.e("ERROR_DELETE", e.message.toString())
            ProcessState.FAILURE
        }
    }

    fun counter1(user: String,liveData: MutableLiveData<String>) : String {
        var result = 0
        runBlocking {
            myRef.child(user).orderByChild("type").equalTo(TaskType.IMPORTANT_URGENT.value)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        result = 0
                        for (postSnapshot in snapshot.children) {
                            Log.i("DATA1",postSnapshot.toString())
                            result++
                        }
                        liveData.postValue(result.toString())
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.e("READ_FIRE1", "Failed to read value.", error.toException())
                    }

                })
        }


        return result.toString()
    }
    fun counter2(user: String,liveData: MutableLiveData<String>) : String {
        var result = 0
        runBlocking {
            myRef.child(user).orderByChild("type").equalTo(TaskType.IMPORTANT_NOT_URGENT.value)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        result = 0
                        for (postSnapshot in snapshot.children) {
                            Log.i("DATA2",postSnapshot.toString())
                            result++
                        }
                        liveData.postValue(result.toString())
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.e("READ_FIRE2", "Failed to read value.", error.toException())
                    }

                })
        }


        return result.toString()
    }
    fun counter3(user: String,liveData: MutableLiveData<String>) : String {
        var result = 0
        runBlocking {
            myRef.child(user).orderByChild("type").equalTo(TaskType.NOT_IMPORTANT_URGENT.value)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        result = 0
                        for (postSnapshot in snapshot.children) {
                            Log.i("DATA3",postSnapshot.toString())
                            result++
                        }
                        liveData.postValue(result.toString())
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.e("READ_FIRE3", "Failed to read value.", error.toException())
                    }

                })
        }


        return result.toString()
    }
    fun counter4(user: String,liveData: MutableLiveData<String>) : String {
        var result = 0
        runBlocking {
            myRef.child(user).orderByChild("type").equalTo(TaskType.NOT_IMPORTANT_NOT_URGENT.value)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        result = 0
                        for (postSnapshot in snapshot.children) {
                            Log.i("DATA4", postSnapshot.toString())
                            result++
                        }
                        liveData.postValue(result.toString())
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.e("READ_FIRE4", "Failed to read value.", error.toException())
                    }

                })

        }

        return result.toString()
    }
    fun counterUndefined(user: String,liveData: MutableLiveData<String>) : String {
        var result = 0
        runBlocking {
            myRef.child(user).orderByChild("type").equalTo(TaskType.UNDEFINED.value)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        result = 0
                        for (postSnapshot in snapshot.children) {
                            Log.i("DATA4", postSnapshot.toString())
                            result++
                        }
                        liveData.postValue(result.toString())
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.e("READ_FIRE4", "Failed to read value.", error.toException())
                    }

                })

        }

        return result.toString()
    }

    fun fetchTasks(user : String,type: TaskType,liveData: MutableLiveData<List<Task>>) {
        myRef.child(user)
            .orderByChild("type").equalTo(type.value)
            .addValueEventListener(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
//                            var tasks = ArrayList<Task>()

//                    for (postSnapshot in snapshot.children) {
//                        Log.i("TASKS",postSnapshot.toString())
//                        tasks.add(postSnapshot.getValue(Task::class.java)!!)
//                    }
                  var  tasks = snapshot.children.map { dataSnapshot ->
                        dataSnapshot.getValue(Task::class.java)!!
                    }
                    liveData.postValue(tasks)
                }

                override fun onCancelled(error: DatabaseError) {
                    // Nothing to do
                }
            })
    }

    enum class ProcessState(val value: String) {
        LOADING("LOADING"),
        SUCCESS("SUCCESS"),
        FAILURE("FAILURE"),
    }
    init {
//        authAppRepository = AuthAppRepository()
//        userLiveData = authAppRepository.userLiveData
//        count1.value = getCounter1(userLiveData.value!!.uid)
    }
    class Counters {
        var type1: Int = 0;
        var type2: Int = 0;
        var type3: Int = 0;
        var type4: Int = 0;
    }
}






