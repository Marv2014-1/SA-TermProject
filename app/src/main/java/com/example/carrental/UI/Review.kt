package com.example.carrental.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.carrental.Logic.Mediator.Mediator
import com.example.carrental.R
import com.example.carrental.databinding.ActivityFilterCarBinding
import com.example.carrental.databinding.ActivityReviewBinding
import com.example.carrental.databinding.ActivityUpdateCarBinding

class Review : AppCompatActivity() {

    lateinit var binding: ActivityReviewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extras = intent.extras
        var value : Long = 0
        if (extras != null){
            val temp = extras.getString("target").toString()
            value = temp.toLong()
        }

        binding.reviewBack.setOnClickListener(){
            Mediator.back(this)
        }

        binding.sendButon.setOnClickListener(){
            var num = binding.reviewNum.text.toString().toDouble()

            if (num > 5.0){
                num = 5.0
            }
            Mediator.review(this, value, num)
        }
    }
}