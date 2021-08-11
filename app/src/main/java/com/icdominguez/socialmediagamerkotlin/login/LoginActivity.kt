package com.icdominguez.socialmediagamerkotlin.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.icdominguez.socialmediagamerkotlin.databinding.ActivityLoginBinding
import com.icdominguez.socialmediagamerkotlin.home.HomeRouter

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        binding.textViewRegister.setOnClickListener {
            LoginRouter().launch(applicationContext)
        }
    }

    override fun onStart() {
        super.onStart()

        if(viewModel.checkUser()) {
            HomeRouter().launch(applicationContext)
        }
    }


}

