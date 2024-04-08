package com.example.carrental.database.model

/**
 * this class models the car and it's variables as seen on the car table
 */

class Car {
    var id : Long = -1
    var owner : Long = -1
    var model : String = ""
    var year : String = ""
    var mileage : Int = 0
    var availability : String = ""
    var location : String = ""
    var price : Int = 0
    var renter : Long = -1
    var ownerUsername : String = ""

    constructor(){
        id = -1
        owner = -1
        model = ""
        year = ""
        mileage = 0
        availability = ""
        location = ""
        price = 0
        renter = -1
    }

    constructor(id : Long, owner : Long, model : String, year : String, mileage : Int,
                availability : String, location : String, price : Int, renter : Long){
        this.id = id
        this.owner = owner
        this.model = model
        this.year = year
        this.mileage = mileage
        this.availability = availability
        this.location = location
        this.price = price
        this.renter = renter
    }

    constructor(owner : Long, model : String, year : String, mileage : Int,
                availability : String, location : String, price : Int, renter : Long){
        this.owner = owner
        this.model = model
        this.year = year
        this.mileage = mileage
        this.availability = availability
        this.location = location
        this.price = price
        this.renter = renter
    }

    fun setUsername(ownerUsername: String){
        this.ownerUsername = ownerUsername
    }

}