package com.example.carrental.UI

/**
 * this class is a UI allows the user to viw and manage their cars
 */

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carrental.Logic.Mediator.Mediator
import com.example.carrental.R
import com.example.carrental.UI.Adapters.GarageAdapter
import com.example.carrental.database.model.Car
import com.example.carrental.databinding.ActivityForgotPasswordBinding
import com.example.carrental.databinding.ActivityGarageBinding


class Garage : AppCompatActivity(), GarageAdapter.OnButtonClickListener {
    private lateinit var binding : ActivityGarageBinding
    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<Car>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGarageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpAdapter()

        binding.addCar.setOnClickListener(){
            Mediator.newCar(this)
        }

        binding.garageBack.setOnClickListener(){
            Mediator.back(this)
        }
    }

    // sets up the recycle view adapter and passes the array to be used
    private fun setUpAdapter(){
        newArrayList = Mediator.getUserCars(this)

        newRecyclerView = binding.garageCarList
        newRecyclerView.layoutManager = LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true)

        var adapter = GarageAdapter(newArrayList, this)
        newRecyclerView.adapter = adapter
    }

    // adds button functionality to recyclerview buttons
    override fun onButtonClick(position: Int, buttonId: Int) {
        if (R.id.garageDeleteCar == buttonId){
            val carId = newArrayList.get(position).id
            Mediator.deleteCar(this, carId)
        }
        else if (R.id.garageEditCar == buttonId){
            val carId = newArrayList.get(position).id
            Mediator.editCar(this ,carId)
        }
    }
}