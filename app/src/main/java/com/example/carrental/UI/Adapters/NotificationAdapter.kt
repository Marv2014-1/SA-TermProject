package com.example.carrental.UI.Adapters

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

    interface OnButtonClickListener{
        fun onButtonClick(position: Int, buttonId: Int)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NotificationAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.notification, parent, false )
        return NotificationAdapter.MyViewHolder(itemView, listener)
    }

    override fun getItemCount(): Int {
        return notificationList.size
    }

    override fun onBindViewHolder(holder: NotificationAdapter.MyViewHolder, position: Int) {
        val currentItem = notificationList[position]
        holder.writableArea.text = currentItem
    }


    class MyViewHolder(itemView : View, listener: NotificationAdapter.OnButtonClickListener) : RecyclerView.ViewHolder(itemView){
        val writableArea : TextView = itemView.findViewById(R.id.notificationText)
    }

}