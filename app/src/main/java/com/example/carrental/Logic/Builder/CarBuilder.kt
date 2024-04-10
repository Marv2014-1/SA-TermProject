package com.example.carrental.Logic.Builder

/**
 * this is the concrete builder
 */

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

    // set id
    override fun setId(id: Long) {
        this.id = id
    }

    // set owner
    override fun setOwner(owner: Long) {
        this.owner = owner
    }

    // set model
    override fun setModel(model: String) {
        this.model = model
    }

    // set year
    override fun setYear(year: String) {
        this.year = year
    }

    // set mileage
    override fun setMileage(mileage: Int) {
        this.mileage = mileage
    }

    // set availability
    override fun setAvailability(availability: String) {
        this.availability = availability
    }

    // set location
    override fun setLocation(location: String) {
        this.location = location
    }

    // set price
    override fun setPrice(price: Int) {
        this.price = price
    }

    // set renter
    override fun setRenter(renter: Long) {
        this.renter = renter
    }

    // get result
    fun getResult() : Car{
        var car = Car(id, owner, model, year, mileage, availability, location, price, renter)

        return car
    }
}