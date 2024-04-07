package com.example.carrental.database.model

/**
 * this class models the user and it's variables as seen on the user table
 */

import com.example.carrental.UI.ForgotPassword

class User {
    var id: Long? = null
    var balance: Long? = null
    var username : String? = null
    var password : String? = null
    var Q1 : String? = null
    var Q2 : String? = null
    var Q3 : String? = null

    constructor(){
        id = -1
        username = ""
        password = ""
        Q1 = ""
        Q2 = ""
        Q3 = ""
        balance = 0
    }

    constructor(username : String, password: String, Q1 : String, Q2 : String, Q3 : String, balance : Long){
        this.id = -1
        this.username = username
        this.password = password
        this.Q1 = Q1
        this.Q2 = Q2
        this.Q3 = Q3
        this.balance = balance
    }

    constructor(id : Long, username : String, password: String, Q1 : String, Q2 : String, Q3 : String, balance : Long){
        this.id = id
        this.username = username
        this.password = password
        this.Q1 = Q1
        this.Q2 = Q2
        this.Q3 = Q3
        this.balance = balance
    }

    constructor(username : String, Q1 : String, Q2 : String, Q3 : String){
        this.id = -1
        this.username = username
        this.Q1 = Q1
        this.Q2 = Q2
        this.Q3 = Q3
    }

    constructor(username : String, password: String){
        this.username = username
        this.password = password
    }
}