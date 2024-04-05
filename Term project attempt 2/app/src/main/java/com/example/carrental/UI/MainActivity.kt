package com.example.carrental.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.carrental.Logic.Mediator.Mediator
import com.example.carrental.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(){
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extras = intent.extras
        if (extras != null){
            val value = extras.getString("state")
            if (value != null) {
                binding.mainError.text = value
            }

            val error = extras.getString("error")
            if (error != null){
                binding.mainError.text = error
            }
        }

        binding.login.setOnClickListener(){
            val username = binding.username.text.toString()
            val password = binding.password.text.toString()
            if (username != "" && password != ""){
                Mediator.login(this, username, password)
            } else {
                val error = "Please Input Both a Username AND Password"
                binding.mainError.text = error
            }
        }

        binding.forgotPassword.setOnClickListener(){
            Mediator.forgotPassword(this)
        }

        binding.newUser.setOnClickListener(){
            Mediator.newUser(this)
        }
    }
}