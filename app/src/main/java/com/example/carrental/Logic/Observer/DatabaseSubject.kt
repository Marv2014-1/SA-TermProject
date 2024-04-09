package com.example.carrental.Logic.Observer

import android.content.Context
import com.example.carrental.database.MessageTable
import com.example.carrental.database.ReviewTable

class DatabaseSubject {
    private val observers = mutableListOf<DatabaseObserver>()

    fun searchDatabaseForUpdates(context: Context, userId : Long){
        val messages = MessageTable(context)
        val rentals = MessageTable(context)
        val reviews = ReviewTable(context)


    }
}