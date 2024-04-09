package com.example.carrental.UI.Adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.carrental.Logic.Mediator.Mediator
import com.example.carrental.R
import com.example.carrental.database.model.Car
import com.example.carrental.database.model.Rental

class HistoryAdapter(private val context : Context ,private val rentalList: ArrayList<Rental>, private val listener: HistoryAdapter.OnButtonClickListener) : RecyclerView.Adapter<HistoryAdapter.MyViewHolder>() {

    interface OnButtonClickListener{
        fun onButtonClick(position: Int, buttonId: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.history_rental, parent, false )
        return HistoryAdapter.MyViewHolder(itemView, listener)
    }

    override fun onBindViewHolder(holder: HistoryAdapter.MyViewHolder, position: Int) {
        val currentItem = rentalList[position]
        holder.owner.text = Mediator.getUserName(context , currentItem.owner!!)
        holder.model.text = currentItem.model
        holder.mileage.text = currentItem.mileage.toString()
        holder.year.text = currentItem.year
        holder.renter.text = Mediator.getUserName(context , currentItem.renter!!)
        holder.location.text = currentItem.location
        holder.date.text = currentItem.date
        holder.price.text = currentItem.price.toString()
    }

    override fun getItemCount(): Int {
        return rentalList.size
    }

    class MyViewHolder(itemView : View, listener: HistoryAdapter.OnButtonClickListener) : RecyclerView.ViewHolder(itemView){
        val owner : TextView = itemView.findViewById(R.id.historyOwner)
        val model : TextView = itemView.findViewById(R.id.historyCar)
        val mileage : TextView = itemView.findViewById(R.id.historyMileage)
        val year : TextView = itemView.findViewById(R.id.historyYear)
        val renter : TextView = itemView.findViewById(R.id.historyRenter)
        val location : TextView = itemView.findViewById(R.id.historyLocation)
        val date : TextView = itemView.findViewById(R.id.historyDate)
        val price : TextView = itemView.findViewById(R.id.historyPrice)
    }
}