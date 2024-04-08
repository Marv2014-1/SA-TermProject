package com.example.carrental.UI

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carrental.Logic.Mediator.Mediator
import com.example.carrental.R
import com.example.carrental.UI.Adapters.MenuAdapter
import com.example.carrental.UI.Adapters.PeopleAdapter
import com.example.carrental.database.model.Car
import com.example.carrental.database.model.User
import com.example.carrental.databinding.ActivityForgotPasswordBinding
import com.example.carrental.databinding.ActivityPeopleBinding

class People : AppCompatActivity(), PeopleAdapter.OnButtonClickListener {
    private lateinit var binding : ActivityPeopleBinding
    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPeopleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpAdapter(this)

        binding.peopleRevert.setOnClickListener(){
            Mediator.back(this)
        }
    }

    private fun setUpAdapter(context : Context){
        newArrayList = Mediator.getUsers(this)

        newRecyclerView = binding.peopleList
        newRecyclerView.layoutManager = LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true)

        var adapter = PeopleAdapter(context ,newArrayList, this)
        newRecyclerView.adapter = adapter
    }

    override fun onButtonClick(position: Int, buttonId: Int) {
        if (R.id.peopleMessage == buttonId){
            val personId = newArrayList.get(position).id
        }
        else if (R.id.peopleReview == buttonId){
            val personId = newArrayList.get(position).id
            Mediator.reviewPage(this, newArrayList.get(position).id.toString().toLong())
        }
    }
}