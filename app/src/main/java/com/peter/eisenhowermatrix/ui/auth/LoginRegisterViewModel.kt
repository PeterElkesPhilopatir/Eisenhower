package com.peter.eisenhowermatrix.ui.auth

import android.app.Activity
import com.google.firebase.auth.FirebaseUser

import androidx.lifecycle.MutableLiveData

import com.peter.eisenhowermatrix.service.AuthAppRepository

import android.app.Application

import androidx.annotation.NonNull

import androidx.lifecycle.AndroidViewModel


class LoginRegisterViewModel(application: Application) : AndroidViewModel(application) {
    var email : String = ""
    var password : String = ""
    private val authAppRepository: AuthAppRepository
    val userLiveData: MutableLiveData<FirebaseUser?>

    fun  login() {
        authAppRepository.login(email, password)
    }

    fun register() {
        authAppRepository.register(email, password)
    }

    init {
        authAppRepository = AuthAppRepository(application)
        userLiveData = authAppRepository.userLiveData
    }

    fun signInGoogle(activity: Activity){
//        val signInIntent = googleSignInClient.signInIntent
//        application.startActivityForResult(signInIntent, 9001)
    }
}