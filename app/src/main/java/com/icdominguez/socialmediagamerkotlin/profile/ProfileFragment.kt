package com.icdominguez.socialmediagamerkotlin.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.icdominguez.socialmediagamerkotlin.R
import com.icdominguez.socialmediagamerkotlin.databinding.FragmentFilterBinding

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentFilterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFilterBinding.inflate(layoutInflater)
        return binding.root
    }
}