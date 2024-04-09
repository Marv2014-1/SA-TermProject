package com.example.carrental.database.model

import android.os.Build
import android.os.OutcomeReceiver
import androidx.annotation.RequiresApi
import java.sql.Timestamp
import java.time.LocalTime

class Message {
    var id : Long = -1
    var text : String = ""
    var senderId: Long = -1
    var receiverId: Long = -1
    @RequiresApi(Build.VERSION_CODES.O)
    var timestamp: String = LocalTime.now().hour.toString() + ":" + LocalTime.now().minute.toString()

    @RequiresApi(Build.VERSION_CODES.O)
    constructor(id : Long, text : String, senderId : Long, receiverId: Long, timestamp: String){
        this.id = id
        this.text = text
        this.senderId = senderId
        this.receiverId = receiverId
        this.timestamp = timestamp
    }

    @RequiresApi(Build.VERSION_CODES.O)
    constructor(text : String, senderId : Long, receiverId: Long){
        this.text = text
        this.senderId = senderId
        this.receiverId = receiverId
        this.timestamp = LocalTime.now().hour.toString() + ":" + LocalTime.now().minute.toString()
    }
}