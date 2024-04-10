package com.example.carrental.UI

/**
 * this class is a UI allows the user to send messages
 */

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carrental.Logic.Mediator.Mediator
import com.example.carrental.UI.Adapters.MenuAdapter
import com.example.carrental.UI.Adapters.MessageAdapter
import com.example.carrental.database.model.Car
import com.example.carrental.database.model.Message
import com.example.carrental.databinding.ActivityMessagesBinding

class MessageListing : AppCompatActivity(), MessageAdapter.OnButtonClickListener{
    lateinit var binding : ActivityMessagesBinding
    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<Message>
    private var target : Long = -1
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessagesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extras = intent.extras
        if (extras != null){
            target = extras.getLong("Id")
        }

        setUpAdapter(this)

        binding.messagesRevert.setOnClickListener(){
            Mediator.back(this)
        }

        binding.sendMessageButton.setOnClickListener(){
            Mediator.sendMessage(this, target, binding.messageEdit.text.toString())
        }
    }

    // sets up the recycle view adapter and passes the array to be used
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setUpAdapter(context: Context){
        newArrayList = Mediator.getMessages(context, target)
        newArrayList.sortWith(TimeComparator())

        newRecyclerView = binding.messageList
        newRecyclerView.layoutManager = LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true)

        var adapter = MessageAdapter(context, newArrayList, this)
        newRecyclerView.adapter = adapter
    }

    //this is a comparator to list the messages in order
    class TimeComparator : Comparator<Message> {
        @RequiresApi(Build.VERSION_CODES.O)
        override fun compare(time1: Message, time2: Message): Int {
            val (hour1, minute1) = time1.timestamp.split(":").map { it.toInt() }
            val (hour2, minute2) = time2.timestamp.split(":").map { it.toInt() }

            if (hour1 != hour2) {
                return hour1.compareTo(hour2)
            } else {
                return minute1.compareTo(minute2)
            }
        }
    }

    // adds button functionality to recyclerview buttons
    override fun onButtonClick(position: Int, buttonId: Int) {
        TODO("Not yet implemented")
    }
}