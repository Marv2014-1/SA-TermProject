package com.example.carrental.Logic.Builder

interface Builder {
    fun setOwner(owner : Long)
    fun setModel(model : String)
    fun setYear(year : String)
    fun setMileage(mileage : Int)
    fun setAvailability(availability : String)
    fun setLocation(location : String)
    fun setPrice(price : Int)
    fun setRenter(renter : Long)
}