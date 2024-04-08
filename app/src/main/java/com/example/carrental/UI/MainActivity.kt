package com.example.carrental.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.carrental.Logic.Mediator.Mediator
import com.example.carrental.database.DbHelper
import com.example.carrental.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(){
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//         This will delete the database entirely (disabled)
//        val database = DbHelper.getInstance(this)
//        database.deleteAll()

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

        // Will the the user to the login page if entered correctly (display error otherwise)
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

        // will take user to the forgot password page
        binding.forgotPassword.setOnClickListener(){
            Mediator.forgotPassword(this)
        }

        // will take the user to the account creation page
        binding.newUser.setOnClickListener(){
            Mediator.newUser(this)
        }
    }
}