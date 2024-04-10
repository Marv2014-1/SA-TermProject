package com.example.carrental.UI.Adapters

/**
 * This class acts as an adapter to the Notification recycler view
 */

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.carrental.Logic.Mediator.Mediator
import com.example.carrental.R
import com.example.carrental.database.model.Message

class NotificationAdapter (private val context: Context, private val notificationList: ArrayList<String>, private val listener: NotificationAdapter.OnButtonClickListener) : RecyclerView.Adapter<NotificationAdapter.MyViewHolder>() {

    //Define button click functionality
    interface OnButtonClickListener{
        fun onButtonClick(position: Int, buttonId: Int)
    }

    //Define items as they appear in the context
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NotificationAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.notification, parent, false )
        return NotificationAdapter.MyViewHolder(itemView, listener)
    }

    //Define how many items there are
    override fun getItemCount(): Int {
        return notificationList.size
    }

    //Set updated text text
    override fun onBindViewHolder(holder: NotificationAdapter.MyViewHolder, position: Int) {
        val currentItem = notificationList[position]
        holder.writableArea.text = currentItem
    }

    //Bind items to named variables for access
    class MyViewHolder(itemView : View, listener: NotificationAdapter.OnButtonClickListener) : RecyclerView.ViewHolder(itemView){
        val writableArea : TextView = itemView.findViewById(R.id.notificationText)
    }

}