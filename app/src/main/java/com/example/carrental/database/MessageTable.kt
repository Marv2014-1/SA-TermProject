package com.example.carrental.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.carrental.database.model.Message
import com.example.carrental.database.model.Rental

class MessageTable (private val context: Context) : DataFunctions <Long , Message>{

    companion object{
        private val MESSAGE_TABLE_NAME = "messages"
        private val MESSAGE_TABLE_ID = "id"
        private val MESSAGE_TABLE_SENDER = "sender"
        private val MESSAGE_TABLE_RECEIVER = "receiver"
        private val MESSAGE_TABLE_TEXT = "text"
        private val MESSAGE_TABLE_TIME = "time"

    }

    override fun getALL(): List<Message> {
        TODO("Not yet implemented")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("Range")
    fun getCurrentConvo(userId: Long, targetId: Long) : ArrayList<Message>{
        val database = DbHelper.getInstance(context)
        val selectQuery = "SELECT * FROM $MESSAGE_TABLE_NAME WHERE " +
                " ($MESSAGE_TABLE_SENDER = \"$userId\" AND $MESSAGE_TABLE_RECEIVER = \"$targetId\") " +
                " OR ($MESSAGE_TABLE_SENDER = \"$targetId\" AND $MESSAGE_TABLE_RECEIVER = \"$userId\") " +
                "ORDER BY $MESSAGE_TABLE_TIME DESC"

        val db : SQLiteDatabase = database.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        var messages : ArrayList<Message> = ArrayList()

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    var id = cursor.getLong(cursor.getColumnIndex(MESSAGE_TABLE_ID))
                    var sender = cursor.getLong(cursor.getColumnIndex(MESSAGE_TABLE_SENDER))
                    var receiver = cursor.getLong(cursor.getColumnIndex(MESSAGE_TABLE_RECEIVER))
                    var text = cursor.getString(cursor.getColumnIndex(MESSAGE_TABLE_TEXT))
                    var time = cursor.getString(cursor.getColumnIndex(MESSAGE_TABLE_TIME))

                    var message = Message(id,text,sender,receiver,time)

                    messages.add(message)


                }while (cursor.moveToNext())
            }
        }

        cursor.close()
        database.close()
        return messages
    }

    override fun deleteAll(): Boolean {
        val database= DbHelper.getInstance(context)
        var deletedAll = false

        database.writableDatabase.use { db ->
            db.delete(MESSAGE_TABLE_NAME, null, null)

            val count = getCount()

            if (count == 0L)
                deletedAll = true
        }

        return deletedAll
    }

    fun getCount(): Long{
        val appDbHelper = DbHelper.getInstance(context)
        val db = appDbHelper.writableDatabase
        return DatabaseUtils.queryNumEntries(db, MESSAGE_TABLE_NAME)
    }

    override fun deleteById(id: Long): Int {
        TODO("Not yet implemented")
    }

    override fun delete(t: Message) {
        TODO("Not yet implemented")
    }

    override fun update(t: Message) {
        TODO("Not yet implemented")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun insert(message: Message): Long? {
        var id: Long = -1
        val database= DbHelper.getInstance(context)

        try {
            database.writableDatabase.use { db ->
                val content = ContentValues()

                content.put(MESSAGE_TABLE_SENDER, message.senderId)
                content.put(MESSAGE_TABLE_RECEIVER, message.receiverId)
                content.put(MESSAGE_TABLE_TEXT, message.text)
                content.put(MESSAGE_TABLE_TIME, message.timestamp)



                id = db.insert(MESSAGE_TABLE_NAME, null, content)

                message.id = id
            }
        } catch (e : SQLiteException){
            println(e)
        }

        database.close()
        return id
    }

    override fun getByID(id: Long): Message? {
        TODO("Not yet implemented")
    }
}