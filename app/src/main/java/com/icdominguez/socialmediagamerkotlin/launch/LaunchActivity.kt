package com.icdominguez.socialmediagamerkotlin.launch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import androidx.lifecycle.ViewModelProvider
import com.icdominguez.socialmediagamerkotlin.R
import com.icdominguez.socialmediagamerkotlin.databinding.ActivityLaunchBinding
import com.icdominguez.socialmediagamerkotlin.home.HomeRouter
import com.icdominguez.socialmediagamerkotlin.login.LoginRouter
import com.icdominguez.socialmediagamerkotlin.login.LoginViewModel
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LaunchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLaunchBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)
        binding = ActivityLaunchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)


        GlobalScope.launch {
            checkUser()
        }

    }

    suspend fun checkUser() {
        if(viewModel.checkUser()) {
            HomeRouter().launch(applicationContext)
        } else {
            LoginRouter().launch(applicationContext)
        }
    }
}