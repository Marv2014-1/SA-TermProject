package com.example.carrental.Logic.Observer

/**
 * this class will be the reciver to th subject class
 */

import com.example.carrental.Logic.Singleton.Session

class UserObserver() : DatabaseObserver {
    //Will add messages tot he session notification variable
    override fun onDatabaseUpdated(item: String) {
        val session = Session.getInstance()
        session.addNotification(item)
    }
}