package com.icdominguez.socialmediagamerkotlin.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.CollectionReference
import com.icdominguez.socialmediagamerkotlin.R
import com.icdominguez.socialmediagamerkotlin.chat.ChatFragment
import com.icdominguez.socialmediagamerkotlin.common.Constants
import com.icdominguez.socialmediagamerkotlin.common.ResultOf
import com.icdominguez.socialmediagamerkotlin.databinding.ActivityHomeBinding
import com.icdominguez.socialmediagamerkotlin.filter.FilterFragment
import com.icdominguez.socialmediagamerkotlin.profile.ProfileFragment

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModel.tokenStatus.observe(this, Observer { result -> result?.let{
            when(it) {
                is ResultOf.Success -> {
                    if(it.value == Constants.TOKEN_CREATED) {
                        Toast.makeText(this, "Token successfully created", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Token failed with ${it.value}", Toast.LENGTH_SHORT).show()
                    }
                }

                is ResultOf.Failure -> {
                    val failedMessage = it.message ?: "Unknown Error"
                    Toast.makeText(this, "Token failed with $failedMessage", Toast.LENGTH_SHORT).show()
                }
            }
        } })
        viewModel.createToken()

        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in, R.anim.fade_out)

        binding.bottomNavigationView.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.nav_home -> {
                    transaction.replace(R.id.container, HomeFragment())
                    true
                }
                R.id.nav_filter -> {
                    transaction.replace(R.id.container, FilterFragment())
                    true
                }
                R.id.nav_chat -> {
                    transaction.replace(R.id.container, ChatFragment())
                    true
                }
                R.id.nav_profile -> {
                    transaction.replace(R.id.container, ProfileFragment())
                    true
                }
                else ->  false
            }
        }



    }
}