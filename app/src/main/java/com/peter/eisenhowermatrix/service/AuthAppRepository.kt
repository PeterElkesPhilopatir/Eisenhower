package com.peter.eisenhowermatrix.service

import android.os.Build
import androidx.lifecycle.MutableLiveData

import com.google.firebase.auth.FirebaseUser

import android.widget.Toast

import com.google.firebase.auth.AuthResult

import androidx.annotation.NonNull

import com.google.android.gms.tasks.OnCompleteListener

import com.google.firebase.auth.FirebaseAuth

import android.app.Application
import android.content.Context
import android.provider.Settings.Global.getString
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.peter.eisenhowermatrix.R


class AuthAppRepository(private val application: Application) {
    private val firebaseAuth: FirebaseAuth
    val userLiveData: MutableLiveData<FirebaseUser?>
    val loggedOutLiveData: MutableLiveData<Boolean>

    fun login(email: String?, password: String?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            firebaseAuth.signInWithEmailAndPassword(email!!, password!!)
                .addOnCompleteListener(application.mainExecutor,
                    { task ->
                        if (task.isSuccessful) {
                            userLiveData.value = firebaseAuth.currentUser
                            Toast.makeText(
                                application.applicationContext,
                                "Welcome " + firebaseAuth.currentUser!!.email,
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                application.applicationContext,
                                "Login Failure: " + task.exception!!.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
        }
    }

    fun register(email: String?, password: String?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            firebaseAuth.createUserWithEmailAndPassword(email!!, password!!)
                .addOnCompleteListener(application.mainExecutor,
                    { task ->
                        if (task.isSuccessful) {
                            userLiveData.value = firebaseAuth.currentUser
                            login(email, password)
                        } else {
                            Toast.makeText(
                                application.applicationContext,
                                "Registration Failure: " + task.exception!!.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
        }
    }

    fun logOut() {
        firebaseAuth.signOut()
        loggedOutLiveData.postValue(true)
        userLiveData.postValue(null)
    }

    init {
        firebaseAuth = FirebaseAuth.getInstance()
        userLiveData = MutableLiveData()
        loggedOutLiveData = MutableLiveData()
        if (firebaseAuth.currentUser != null) {
            userLiveData.value = firebaseAuth.currentUser
            loggedOutLiveData.postValue(false)
        } else {
            Log.e("ERROR", "current user is null")
        }
    }

}