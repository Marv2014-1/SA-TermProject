package com.example.carrental.UI

/**
 * this class is a UI allows the user to filter a car based on their input
 */

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.carrental.Logic.Mediator.Mediator
import com.example.carrental.databinding.ActivityFilterCarBinding

class FilterCar : AppCompatActivity() {
    private lateinit var binding : ActivityFilterCarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilterCarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.filterButton.setOnClickListener(){
            var model = binding.filterModel.text.toString()
            var availability = binding.filterAvailability.text.toString()
            var location = binding.filterLocation.text.toString()
            var low = binding.filterLow.text.toString().toInt()
            var high = binding.filterHigh.text.toString().toInt()

            Mediator.setFilter(this, model, availability, location, low, high)
        }

        binding.filterBack.setOnClickListener(){
            Mediator.back(this)
        }
    }
}