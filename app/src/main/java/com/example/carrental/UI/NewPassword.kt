package com.example.carrental.UI

/**
 * this class is a UI allows the user to change their password
 */


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.carrental.Logic.Mediator.Mediator
import com.example.carrental.databinding.ActivityNewPasswordBinding

class NewPassword : AppCompatActivity() {
    private lateinit var binding: ActivityNewPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var username : String = ""
        val extras = intent.extras
        if (extras != null){
            val value = extras.getString("username")
            if (value != null) {
                username = value
            }
        }

        binding.backNewPassword.setOnClickListener(){
            Mediator.back(this)
        }

        binding.submitNewPassword.setOnClickListener(){
            val first = binding.firstNewPassword.text.toString()
            val second = binding.secondNewPassword.text.toString()
            if (first != "") {
                if (first != second) {
                    val error = "The passwords do not match"
                    binding.errorNewPassword.text = error
                } else {
                    Mediator.newPassword(this, username, first)
                }
            } else {
                val error = "The passwords can not be empty"
                binding.errorNewPassword.text = error
            }
        }
    }
}