package com.example.carrental.UI

/**
 * this class is a UI allows the user to change their password
 */

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.carrental.Logic.Mediator.Mediator
import com.example.carrental.databinding.ActivityForgotPasswordBinding

class ForgotPassword : AppCompatActivity() {
    private lateinit var binding : ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extras = intent.extras
        if (extras != null){
            val error = extras.getString("error")
            if (error != null){
                binding.ForgotError.text = error
            }
        }

        binding.backfForgotPassword.setOnClickListener(){
            Mediator.back(this)
        }

        binding.submitForgotPassword.setOnClickListener(){
            val username = binding.ForgotUsername.text.toString()
            val Q1 = binding.ForgotQ1.text.toString()
            val Q2 = binding.ForgotQ2.text.toString()
            val Q3 = binding.ForgotQ3.text.toString()


            Mediator.retrievePassword(this, username, Q1, Q2, Q3)
        }
    }
}