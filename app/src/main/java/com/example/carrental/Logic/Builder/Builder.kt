package com.example.carrental.Logic.Builder

/**
 * this is the car builder interface
 */

interface Builder {
    fun setId(id : Long)
    fun setOwner(owner : Long)
    fun setModel(model : String)
    fun setYear(year : String)
    fun setMileage(mileage : Int)
    fun setAvailability(availability : String)
    fun setLocation(location : String)
    fun setPrice(price : Int)
    fun setRenter(renter : Long)
}