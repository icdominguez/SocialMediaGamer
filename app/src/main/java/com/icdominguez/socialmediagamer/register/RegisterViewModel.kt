package com.icdominguez.socialmediagamer.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import androidx.lifecycle.*

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.icdominguez.socialmediagamer.model.User
import com.icdominguez.socialmediagamer.common.ResultOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class RegisterViewModel : ViewModel() {

    private var auth: FirebaseAuth? = null
    private var storage: FirebaseStorage? = null

    init {
        auth = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance()
    }

    private val _registrationStatus = MutableLiveData<ResultOf<String>>()
    val registrationStatus : LiveData<ResultOf<String>> = _registrationStatus

    fun createUser(user: User) {

        viewModelScope.launch(Dispatchers.IO) {
            var errorCode = -1

            try {
                auth!!.createUserWithEmailAndPassword(user.email!!, user.password!!).addOnCompleteListener { task: Task<AuthResult> ->
                    if(task.isSuccessful) {
                        _registrationStatus.postValue(ResultOf.Success("Registration Failed with ${task.exception}"))
                    } else {
                        _registrationStatus.postValue(ResultOf.Success("userCreated"))
                    }
                }
            } catch (e: Exception) {
                if(errorCode != -1) {
                    _registrationStatus.postValue(ResultOf.Failure("Failed with Error Code $errorCode", e))
                } else {
                    _registrationStatus.postValue(ResultOf.Failure("Failed with Exception Code ${e.message}", e))
                }

            }
        }

    }
}