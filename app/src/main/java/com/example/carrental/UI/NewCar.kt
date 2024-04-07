package com.example.carrental.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.carrental.Logic.Mediator.Mediator
import com.example.carrental.R
import com.example.carrental.database.CarTable
import com.example.carrental.databinding.ActivityMenuBinding
import com.example.carrental.databinding.ActivityNewCarBinding

class NewCar : AppCompatActivity() {
    private lateinit var binding: ActivityNewCarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewCarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.newCarButton.setOnClickListener(){
            val model = binding.newCarModel.text.toString()
            val year = binding.newCarYear.text.toString()
            val mileage = binding.newCarMileage.text.toString().toInt()
            val availability = binding.newCarAvailability.text.toString()
            val location = binding.newCarLocation.text.toString()
            val price = binding.newCarPrice.text.toString().toInt()

            Mediator.addNewCar(this, model, year, mileage, availability, location, price)
        }

        binding.newCarBack.setOnClickListener(){
            Mediator.back(this)
        }
    }
}