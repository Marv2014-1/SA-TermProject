package com.example.carrental.UI

/**
 * this class is a UI allows the user to control the menu
 */

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carrental.Logic.Mediator.Mediator
import com.example.carrental.R
import com.example.carrental.UI.Adapters.GarageAdapter
import com.example.carrental.UI.Adapters.MenuAdapter
import com.example.carrental.database.model.Car
import com.example.carrental.databinding.ActivityMenuBinding

class Menu : AppCompatActivity() , MenuAdapter.OnButtonClickListener{
    private lateinit var binding : ActivityMenuBinding
    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<Car>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpAdapter()

        binding.menuBalance.text = Mediator.getUserBalance().toString()

        binding.menuBack.setOnClickListener(){
            Mediator.back(this)
        }

        binding.myGarage.setOnClickListener(){
            Mediator.garage(this)
        }

        binding.findPeople.setOnClickListener(){
            Mediator.people(this)
        }

        binding.menuHistory.setOnClickListener(){
            Mediator.history(this)
        }

        binding.menuFilter.setOnClickListener(){
            Mediator.filter(this)
        }

        binding.menuNotificatations.setOnClickListener(){
            Mediator.notifications(this)
        }
    }

    // sets up the recycle view adapter and passes the array to be used
    private fun setUpAdapter(){
        newArrayList = Mediator.getOthersCars(this)

        newRecyclerView = binding.menuCarList
        newRecyclerView.layoutManager = LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true)

        var adapter = MenuAdapter(newArrayList, this)
        newRecyclerView.adapter = adapter
    }

    // adds button functionality to recyclerview buttons
    override fun onButtonClick(position: Int, buttonId: Int) {
        if (R.id.menuCarRentButton == buttonId){
            Mediator.rentCar(this, newArrayList.get(position).owner, newArrayList.get(position))
        }
    }
}