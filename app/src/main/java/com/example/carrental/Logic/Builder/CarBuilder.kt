package com.example.carrental.Logic.Builder

import android.util.Log
import com.example.carrental.database.model.Car

class CarBuilder : Builder {

    private var id : Long = -1
    private var owner : Long = -1
    private var model : String = ""
    private var year : String = ""
    private var mileage : Int = 0
    private var availability : String = ""
    private var location : String = ""
    private var price : Int = 0
    private var renter : Long = -1

    override fun setId(id: Long) {
        this.id = id
    }

    override fun setOwner(owner: Long) {
        this.owner = owner
    }

    override fun setModel(model: String) {
        this.model = model
    }

    override fun setYear(year: String) {
        this.year = year
    }

    override fun setMileage(mileage: Int) {
        this.mileage = mileage
    }

    override fun setAvailability(availability: String) {
        this.availability = availability
    }

    override fun setLocation(location: String) {
        this.location = location
    }

    override fun setPrice(price: Int) {
        this.price = price
    }

    override fun setRenter(renter: Long) {
        this.renter = renter
    }

    fun getResult() : Car{
        var car = Car(owner, model, year, mileage, availability, location, price, renter)

        return car
    }
}