package com.example.carrental.UI.Adapters

/**
 * This class acts as an adapter to the menu recycler view
 */

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.carrental.R
import com.example.carrental.database.model.Car

class MenuAdapter (private val carList: ArrayList<Car>, private val listener: MenuAdapter.OnButtonClickListener) : RecyclerView.Adapter<MenuAdapter.MyViewHolder>(){

    //Define button click functionality
    interface OnButtonClickListener{
        fun onButtonClick(position: Int, buttonId: Int)
    }

    //Define items as they appear in the context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.menu_car, parent, false )
        return MenuAdapter.MyViewHolder(itemView, listener)
    }

    //Define how many items there are
    override fun getItemCount(): Int {
        return carList.size
    }

    //Set updated text text
    override fun onBindViewHolder(holder: MenuAdapter.MyViewHolder, position: Int) {
        val currentItem = carList[position]
        holder.model.text = currentItem.model
        holder.year.text = currentItem.year
        holder.mileage.text = currentItem.mileage.toString()
        holder.price.text = currentItem.price.toString()
        holder.location.text = currentItem.location
        holder.duration.text = currentItem.availability
        holder.owner.text = currentItem.ownerUsername
    }

    //Bind items to named variables for access
    class MyViewHolder(itemView : View, listener: MenuAdapter.OnButtonClickListener) : RecyclerView.ViewHolder(itemView){
        val model : TextView = itemView.findViewById(R.id.menuCarModel)
        val year : TextView = itemView.findViewById(R.id.menuCarYear)
        val mileage : TextView = itemView.findViewById(R.id.menuCarMiles)
        val price : TextView = itemView.findViewById(R.id.menuCarPrice)
        val location : TextView = itemView.findViewById(R.id.menuCarLocation)
        val duration : TextView = itemView.findViewById(R.id.menuCarDuration)
        val owner : TextView = itemView.findViewById(R.id.menuCarOwner)

        val rentButton : Button = itemView.findViewById(R.id.menuCarRentButton)

        init {
            rentButton.setOnClickListener() {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    listener.onButtonClick(adapterPosition, R.id.menuCarRentButton)
                }
            }
        }
    }
}