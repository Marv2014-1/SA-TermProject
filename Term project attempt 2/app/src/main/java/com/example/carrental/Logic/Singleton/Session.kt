package com.example.carrental.Logic.Singleton

import com.example.carrental.database.model.User

class Session private constructor(){

    private var user : User? = null

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
        this.user = user
    }

    fun getUser() : User?{
        return this.user
    }

    fun endSession(){
        user = null
    }

}