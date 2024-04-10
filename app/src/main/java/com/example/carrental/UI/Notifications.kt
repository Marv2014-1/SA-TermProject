package com.example.carrental.UI

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carrental.Logic.Mediator.Mediator
import com.example.carrental.Logic.Singleton.Session
import com.example.carrental.R
import com.example.carrental.UI.Adapters.MenuAdapter
import com.example.carrental.UI.Adapters.NotificationAdapter
import com.example.carrental.database.model.Car
import com.example.carrental.databinding.ActivityNewUserBinding
import com.example.carrental.databinding.ActivityNotificationsBinding

class Notifications : AppCompatActivity(), NotificationAdapter.OnButtonClickListener {
    lateinit var binding : ActivityNotificationsBinding
    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpAdapter(this)

        binding.notificationRevert.setOnClickListener(){
            Mediator.back(this)
        }

    }

    private fun setUpAdapter(context: Context){
        newArrayList = Session.getInstance().getNotifications()

        newRecyclerView = binding.notificationList
        newRecyclerView.layoutManager = LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true)

        var adapter = NotificationAdapter(context ,newArrayList, this)
        newRecyclerView.adapter = adapter
    }

    override fun onButtonClick(position: Int, buttonId: Int) {
        TODO("Not yet implemented")
    }
}