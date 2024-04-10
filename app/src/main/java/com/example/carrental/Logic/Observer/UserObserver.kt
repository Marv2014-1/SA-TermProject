package com.example.carrental.Logic.Observer

import com.example.carrental.Logic.Singleton.Session

class UserObserver() : DatabaseObserver {
    override fun onDatabaseUpdated(item: String) {
        val session = Session.getInstance()
        session.addNotification(item)
    }
}