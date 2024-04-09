package com.example.carrental.database.model

/**
 * this class models the rental and it's variables as seen on the rental table
 */

class Rental {
    var id : Long? = null
    var car : Long? = null
    var owner : Long? = null
    var renter : Long? = null
    var price : Long? = null
    var location : String? = null
    var model : String? = null
    var year : String? = null
    var mileage : Int? = null
    var date : String? = null
    var ownerSeen : Boolean = false
    var renterSeen : Boolean = false

    constructor(){
        id = -1
        car = -1
        owner = -1
        renter = -1
        price = -1
        location = ""
        model = ""
        year = ""
        mileage = -1
        date = ""
        ownerSeen = false
        renterSeen = false
    }

    constructor(id : Long, car: Long, owner : Long, renter : Long, price : Long,
                location : String, model : String, year : String, mileage : Int, date : String){
        this.id = id
        this.car = id
        this.owner = owner
        this.renter = renter
        this.price = price
        this.location = location
        this.model = model
        this.year = year
        this.mileage = mileage
        this.date = date
    }

    constructor(car: Long, owner : Long, renter : Long, price : Long,
                location : String, model : String, year : String, mileage : Int, date : String){
        this.car = id
        this.owner = owner
        this.renter = renter
        this.price = price
        this.location = location
        this.model = model
        this.year = year
        this.mileage = mileage
        this.date = date
    }
}