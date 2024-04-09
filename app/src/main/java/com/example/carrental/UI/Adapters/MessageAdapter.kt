package com.example.carrental.UI.Adapters

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.carrental.Logic.Mediator.Mediator
import com.example.carrental.R
import com.example.carrental.database.model.Message

class MessageAdapter(private val context: Context, private val messageList: ArrayList<Message>, private val listener: MessageAdapter.OnButtonClickListener) : RecyclerView.Adapter<MessageAdapter.MyViewHolder>() {

    interface OnButtonClickListener{
        fun onButtonClick(position: Int, buttonId: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.message, parent, false )
        return MessageAdapter.MyViewHolder(itemView, listener)
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MessageAdapter.MyViewHolder, position: Int) {
        val currentItem = messageList[position]
        holder.username.text = Mediator.getUserName(context,currentItem.receiverId)
        holder.time.text = currentItem.timestamp
        holder.text.text = currentItem.text
    }

    class MyViewHolder(itemView : View, listener: MessageAdapter.OnButtonClickListener) : RecyclerView.ViewHolder(itemView){
        val username : TextView = itemView.findViewById(R.id.messageSender)
        val time : TextView = itemView.findViewById(R.id.messageTime)
        val text : TextView = itemView.findViewById(R.id.messageText)

    }
}