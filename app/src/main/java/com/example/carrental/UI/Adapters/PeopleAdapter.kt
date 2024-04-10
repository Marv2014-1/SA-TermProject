package com.example.carrental.UI.Adapters

/**
 * This class acts as an adapter to the People recycler view
 */

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.carrental.Logic.Mediator.Mediator
import com.example.carrental.R
import com.example.carrental.database.model.Car
import com.example.carrental.database.model.User

class PeopleAdapter(private val context: Context,private val peopleList: ArrayList<User>, private val listener: PeopleAdapter.OnButtonClickListener) : RecyclerView.Adapter<PeopleAdapter.MyViewHolder>() {

    //Define button click functionality
    interface OnButtonClickListener{
        fun onButtonClick(position: Int, buttonId: Int)
    }

    //Define items as they appear in the context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.person, parent, false )
        return PeopleAdapter.MyViewHolder(itemView, listener)
    }

    //Define how many items there are
    override fun getItemCount(): Int {
        return peopleList.size
    }

    //Set updated text text
    override fun onBindViewHolder(holder: PeopleAdapter.MyViewHolder, position: Int) {
        val currentItem = peopleList[position]
        holder.username.text = currentItem.username
        var rating = Mediator.getScore(context , currentItem.id!!).toString().toDouble()
        holder.score.text = "${rating * 100} %"
    }

    //Bind items to named variables for access
    class MyViewHolder(itemView : View, listener: PeopleAdapter.OnButtonClickListener) : RecyclerView.ViewHolder(itemView){
        val username : TextView = itemView.findViewById(R.id.peopleUsername)
        val score : TextView = itemView.findViewById(R.id.peopleScore)


        val messageButton : Button = itemView.findViewById(R.id.peopleMessage)
        val reviewButton : Button = itemView.findViewById(R.id.peopleReview)

        init {
            messageButton.setOnClickListener() {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    listener.onButtonClick(adapterPosition, R.id.peopleMessage)
                }
            }

            reviewButton.setOnClickListener() {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    listener.onButtonClick(adapterPosition, R.id.peopleReview)
                }
            }
        }
    }
}