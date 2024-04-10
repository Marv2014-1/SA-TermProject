package com.example.carrental.Logic.Observer

/**
 * this class is the observer interface
 */

interface DatabaseObserver {
    //once an update is sensed, it will send a notification to all users
    fun onDatabaseUpdated(display : String)
}