package com.example.carrental.Logic.Observer

interface DatabaseObserver {
    fun onDatabaseUpdated(display : String)
}