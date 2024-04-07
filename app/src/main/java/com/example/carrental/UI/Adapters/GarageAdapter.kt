package com.example.carrental.UI.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.carrental.R
import com.example.carrental.database.model.Car
import com.example.carrental.databinding.GarageCarBinding

class GarageAdapter(private val carList: ArrayList<Car>, private val listener: OnButtonClickListener) : RecyclerView.Adapter<GarageAdapter.MyViewHolder>() {

    interface OnButtonClickListener{
        fun onButtonClick(position: Int, buttonId: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder{
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.garage_car, parent, false )
        return MyViewHolder(itemView, listener)
    }

    override fun getItemCount(): Int {
        return carList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = carList[position]
        holder.model.text = currentItem.model
        holder.year.text = currentItem.year
        holder.mileage.text = currentItem.mileage.toString()
        holder.price.text = currentItem.price.toString()
        holder.location.text = currentItem.location.toString()
        holder.duration.text = currentItem.availability
    }

    class MyViewHolder(itemView : View, listener: OnButtonClickListener) : RecyclerView.ViewHolder(itemView){
        val model : TextView = itemView.findViewById(R.id.garageModel)
        val year : TextView = itemView.findViewById(R.id.garageYear)
        val mileage : TextView = itemView.findViewById(R.id.garageMileage)
        val price : TextView = itemView.findViewById(R.id.garagePrice)
        val location : TextView = itemView.findViewById(R.id.garageLocation)
        val duration : TextView = itemView.findViewById(R.id.garageDuration)

        val deleteButton : Button = itemView.findViewById(R.id.garageDeleteCar)
        val editButton : Button = itemView.findViewById(R.id.garageEditCar)

        init {
            deleteButton.setOnClickListener() {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    listener.onButtonClick(adapterPosition, R.id.garageDeleteCar)
                }
            }

            editButton.setOnClickListener() {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    listener.onButtonClick(adapterPosition, R.id.garageEditCar)
                }
            }
        }
    }
}