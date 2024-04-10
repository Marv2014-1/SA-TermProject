package com.example.carrental.Logic.Singleton

import com.example.carrental.database.model.User

class Session private constructor(){

    private var user : User = User()
    private var notifications = ArrayList<String>()

    companion object{
        private var session : Session? = null

        @Synchronized
        fun getInstance() : Session {
            if (session == null){
                session = Session()
            }
            return session as Session
        }
    }

    fun startSession(user : User){
        this.user = User()
        this.user.id = user.id
        this.user.username = user.username
        this.user.balance = user.balance
    }

    fun getUser() : User{
        return this.user!!
    }

    fun endSession(){
        user = User()
        notifications = ArrayList<String>()
    }

    fun addNotification(text : String){
        this.notifications.add(text)
    }

    fun getNotifications() : ArrayList<String>{
        return this.notifications
    }

}