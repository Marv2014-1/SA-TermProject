package com.example.carrental.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.carrental.Logic.Mediator.Mediator
import com.example.carrental.databinding.ActivityNewUserBinding

class NewUser : AppCompatActivity() {
    private lateinit var binding : ActivityNewUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backNewUser.setOnClickListener(){
            Mediator.back(this)
        }

        binding.submitNewUser.setOnClickListener(){
            val username = binding.newUserUsername.text.toString()
            val password = binding.newUserPassword.text.toString()
            val Q1 = binding.questionOne.text.toString()
            val Q2 = binding.questionTwo.text.toString()
            val Q3 = binding.questionThree.text.toString()
            val balance = binding.newUserBalance.text.toString().toLong()

            if (username != "" && password != "" && Q1 != "" && Q2 != "" && Q3 != "" && balance >= 0) {
                Mediator.addNewUser(this, username, password, Q1, Q2, Q3, balance)
            } else {
                val error = "All of the fields must be filled and the balance should be greater than 0"
                binding.newUserError.text = error
            }
        }
    }
}