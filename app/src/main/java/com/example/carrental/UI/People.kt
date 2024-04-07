package com.example.carrental.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.carrental.R
import com.example.carrental.databinding.ActivityForgotPasswordBinding
import com.example.carrental.databinding.ActivityPeopleBinding

class People : AppCompatActivity() {
    private lateinit var binding : ActivityPeopleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPeopleBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}