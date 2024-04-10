package com.example.carrental.UI

/**
 * this class is a UI allows the user to edit a car
 */

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.EditText
import com.example.carrental.Logic.Mediator.Mediator
import com.example.carrental.R
import com.example.carrental.database.model.Car
import com.example.carrental.databinding.ActivityUpdateCarBinding

class UpdateCar : AppCompatActivity() {
    private lateinit var binding : ActivityUpdateCarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateCarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var id : Long = 0
        val extras = intent.extras
        if (extras != null){
            id = extras.getLong("ID")

            val car : Car = Mediator.getCar(this, id)

//            Log.e("car" , car.toString())

            binding.updateCarModel.setText(car.model)
            binding.updateCarYear.setText(car.year)
//            binding.updateCarMileage.setText(car.mileage)
            binding.updateCarAvailability.setText(car.availability)
            binding.updateCarLocation.setText(car.location)
//            binding.updateCarPrice.setText(car.price)
        }

        binding.updateCarButton.setOnClickListener(){
            val model = binding.updateCarModel.text.toString()
            val year = binding.updateCarYear.text.toString()
            val mileage = binding.updateCarMileage.text.toString().toInt()
            val availability = binding.updateCarAvailability.text.toString()
            val location = binding.updateCarLocation.text.toString()
            val price = binding.updateCarPrice.text.toString().toInt()

            Mediator.updateCarContent(this, id,  model, year, mileage, availability, location, price)
        }

        binding.updateCarBack.setOnClickListener(){
            Mediator.back(this)
        }
    }
}