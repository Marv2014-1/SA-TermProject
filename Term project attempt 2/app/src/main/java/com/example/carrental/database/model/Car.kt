package com.example.carrental.database.model

/**
 * this class models the car and it's variables as seen on the car table
 */

class Car {
    var id : Long? = null
    var owner : Long? = null
    var model : String? = null
    var year : String? = null
    var mileage : Int? = null
    var avilability : String? = null
    var location : String? = null
    var price : Int? = null
    var renter : Long? = null

    constructor(){
        id = -1
        owner = -1
        model = ""
        year = ""
        mileage = -1
        avilability = ""
        location = ""
        price = -1
        renter = -1
    }

    constructor(id : Long, owner : Long, model : String, year : String, mileage : Int,
                avilability : String, location : String, price : Int, renter : Long?){
        this.id = id
        this.owner = owner
        this.model = model
        this.year = year
        this.mileage = mileage
        this.avilability = avilability
        this.location = location
        this.price = price
        this.renter = renter
    }

}