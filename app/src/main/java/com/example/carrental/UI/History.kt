package com.example.carrental.UI

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carrental.Logic.Mediator.Mediator
import com.example.carrental.R
import com.example.carrental.UI.Adapters.GarageAdapter
import com.example.carrental.UI.Adapters.HistoryAdapter
import com.example.carrental.database.model.Car
import com.example.carrental.database.model.Rental
import com.example.carrental.databinding.ActivityGarageBinding
import com.example.carrental.databinding.ActivityHistoryBinding

class History : AppCompatActivity(), HistoryAdapter.OnButtonClickListener {
    private lateinit var binding: ActivityHistoryBinding
    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<Rental>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpAdapter(this)

        binding.historyBack.setOnClickListener(){
            Mediator.back(this)
        }
    }

    private fun setUpAdapter(context: Context){
        newArrayList = Mediator.setHistory(context)

        newRecyclerView = binding.historyRental
        newRecyclerView.layoutManager = LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true)

        var adapter = HistoryAdapter(context ,newArrayList, this)
        newRecyclerView.adapter = adapter
    }

    override fun onButtonClick(position: Int, buttonId: Int) {
        TODO("Not yet implemented")
    }
}