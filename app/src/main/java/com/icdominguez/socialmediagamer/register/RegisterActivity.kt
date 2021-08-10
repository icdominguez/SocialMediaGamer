package com.icdominguez.socialmediagamer.register

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.icdominguez.socialmediagamer.R
import com.icdominguez.socialmediagamer.model.User
import com.icdominguez.socialmediagamer.common.Constants
import com.icdominguez.socialmediagamer.common.ResultOf
import com.icdominguez.socialmediagamer.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        viewModel.registrationStatus.observe(this, Observer { result -> result?.let {
            when(it) {
                is ResultOf.Success -> {
                    if(it.value == Constants.USER_CREATED) {
                        Toast.makeText(this, "Registration successfull user created", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Registration failed with ${it.value}", Toast.LENGTH_SHORT).show()
                    }
                }

                is ResultOf.Failure -> {
                    val failedMessage = it.message ?: "Unknown Error"
                    Toast.makeText(this, "Registration failed with $failedMessage", Toast.LENGTH_SHORT).show()
                }
            }
        } })

        binding.buttonRegister.setOnClickListener {
            val email = binding.textEmail
            val password1 = binding.textPassword
            val password2 = binding.textPassword2
            val phone = binding.textPhone
            val username = binding.textUsername


            when {
                email.text.isNullOrEmpty() -> {
                    email.error = getString(R.string.register_empty_email)
                }
                password1.text.toString() != password2.text.toString() -> {
                    password2.error = getString(R.string.register_mismatches_passwords)
                }
                phone.text.isNullOrEmpty() -> {
                    phone.error = getString(R.string.register_empty_phone)
                }
                binding.textUsername.text.isNullOrEmpty() -> {
                    binding.textUsername.error = getString(R.string.register_empty_username)
                }
                else -> {
                    val user = User()
                    user.email = email.text.toString()
                    user.username = username.text.toString()
                    user.phone = username.text.toString()
                    user.password = username.text.toString()

                    viewModel.createUser(user)
                }
            }
        }
    }
}