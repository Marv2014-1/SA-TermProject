package com.example.carrental.Logic.Singleton

import com.example.carrental.database.model.User

class Session private constructor(){

    //the session and notifications
    private var user : User = User()
    private var notifications = ArrayList<String>()

    companion object{
        private var session : Session? = null

        // will get the session instance singleton
        @Synchronized
        fun getInstance() : Session {
            if (session == null){
                session = Session()
            }
            return session as Session
        }
    }

    //will start the session
    fun startSession(user : User){
        this.user = User()
        this.user.id = user.id
        this.user.username = user.username
        this.user.balance = user.balance
    }

    //will return the logged in user
    fun getUser() : User{
        return this.user!!
    }

    // will end the session
    fun endSession(){
        user = User()
        notifications = ArrayList<String>()
    }

    // will add notifications from the databse
    fun addNotification(text : String){
        this.notifications.add(text)
    }

    // will return the notification arraylist
    fun getNotifications() : ArrayList<String>{
        return this.notifications
    }

}