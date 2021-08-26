package com.icdominguez.socialmediagamerkotlin.newPost

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.icdominguez.socialmediagamerkotlin.databinding.ActivityNewPostBinding

class NewPostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewPostBinding
    private lateinit var viewModel: NewPostViewModel
    private lateinit var category: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(NewPostViewModel::class.java)

        setEvents()

    }

    private fun setEvents() {
        binding.imageViewPost1.setOnClickListener {

        }

        binding.imageViewPost2.setOnClickListener {
            openSimpleDialog()
        }

        binding.imageViewPc.setOnClickListener {
            setCategory(1)
        }

        binding.imageViewPs4.setOnClickListener {
            setCategory(2)
        }

        binding.imageViewXbox.setOnClickListener {
            setCategory(3)
        }

        binding.imageViewNintendo.setOnClickListener {
            setCategory(4)
        }

        binding.buttonPost.setOnClickListener {
            savePost()
        }
    }

    private fun savePost() {

    }

    private fun openSimpleDialog() {

    }

    private fun setCategory(category: Int) {
        when(category) {
            1 -> this.category = "PC"
            2 -> this.category = "PS4"
            3 -> this.category = "XBOX"
            4 -> this.category = "NINTENDO"
        }

        binding.textViewCategory.text = this.category
    }
}