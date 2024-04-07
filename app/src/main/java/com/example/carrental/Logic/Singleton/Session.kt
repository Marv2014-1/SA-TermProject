package com.example.carrental.Logic.Singleton

import com.example.carrental.database.model.User

object Session {
    private var user : User = User()

    fun startSession(user : User){
        this.user.id = user.id
        this.user.username = user.username
        this.user.balance = user.balance
    }

    fun getUser() : User {
        return this.user
    }

    fun endSession(){
        this.user = User()
    }
}