package com.icdominguez.socialmediagamerkotlin.login

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : ViewModel() {

    var auth: FirebaseAuth? = null

    init {
        auth = FirebaseAuth.getInstance()
    }

    fun checkUser() : Boolean {
        return auth!!.currentUser != null
    }
}