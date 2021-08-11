package com.icdominguez.socialmediagamerkotlin.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.Token
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.icdominguez.socialmediagamerkotlin.common.Constants
import com.icdominguez.socialmediagamerkotlin.common.ResultOf
import kotlinx.coroutines.launch
import java.lang.Exception

class HomeViewModel : ViewModel() {

    private var collection: CollectionReference? = null
    private var auth: FirebaseAuth? = null

    init {
        collection = FirebaseFirestore.getInstance().collection("tokens")
        auth = FirebaseAuth.getInstance()
    }

    private val _tokenStatus = MutableLiveData<ResultOf<String>>()
    val tokenStatus : LiveData<ResultOf<String>> = _tokenStatus

    fun createToken() {
        var userId = "";

        if(auth!!.currentUser != null) {
            userId = auth!!.currentUser!!.uid
        }


        viewModelScope.launch {
            var errorCode = -1

            try {
                FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                    if(!task.isSuccessful) {
                        _tokenStatus.postValue(ResultOf.Success("Error creating token ${task.exception}"))
                    } else {
                        val token = task.result
                        if (token != null) {
                            collection!!.document(userId).set(token)
                        }
                        _tokenStatus.postValue(ResultOf.Success(Constants.TOKEN_CREATED))
                    }
                }
            } catch (e: Exception) {
                if(errorCode != -1) {
                    _tokenStatus.postValue(ResultOf.Failure("Failed with Error Code $errorCode", e))
                } else {
                    _tokenStatus.postValue(ResultOf.Failure("Failed with Exception Code ${e.message}", e))
                }

            }
        }
    }
}