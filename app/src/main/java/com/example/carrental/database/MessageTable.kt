package com.example.carrental.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.carrental.database.model.Message

class MessageTable (private val context: Context) : DataFunctions <Long , Message>{

    companion object{
        private val MESSAGE_TABLE_NAME = "messages"
        private val MESSAGE_COLUMN_ID = "id"
        private val MESSAGE_COLUMN_SENDER = "sender"
        private val MESSAGE_COLUMN_RECEIVER = "receiver"
        private val MESSAGE_COLUMN_TEXT = "text"
        private val MESSAGE_COLUMN_TIME = "time"
        private val MESSAGE_COLUMN_SEEN = "seen"

    }

    override fun getALL(): List<Message> {
        TODO("Not yet implemented")
    }

    // return all the elements of the current conversation
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("Range")
    fun getCurrentConvo(userId: Long, targetId: Long) : ArrayList<Message>{
        val database = DbHelper.getInstance(context)
        val selectQuery = "SELECT * FROM $MESSAGE_TABLE_NAME WHERE " +
                " ($MESSAGE_COLUMN_SENDER = \"$userId\" AND $MESSAGE_COLUMN_RECEIVER = \"$targetId\") " +
                " OR ($MESSAGE_COLUMN_SENDER = \"$targetId\" AND $MESSAGE_COLUMN_RECEIVER = \"$userId\") " +
                "ORDER BY $MESSAGE_COLUMN_TIME DESC"

        val db : SQLiteDatabase = database.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        var messages : ArrayList<Message> = ArrayList()

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    var id = cursor.getLong(cursor.getColumnIndex(MESSAGE_COLUMN_ID))
                    var sender = cursor.getLong(cursor.getColumnIndex(MESSAGE_COLUMN_SENDER))
                    var receiver = cursor.getLong(cursor.getColumnIndex(MESSAGE_COLUMN_RECEIVER))
                    var text = cursor.getString(cursor.getColumnIndex(MESSAGE_COLUMN_TEXT))
                    var time = cursor.getString(cursor.getColumnIndex(MESSAGE_COLUMN_TIME))

                    var message = Message(id,text,sender,receiver,time)

                    messages.add(message)


                }while (cursor.moveToNext())
            }
        }

        cursor.close()
        database.close()
        return messages
    }

    //if a message has not been seen, it will be fetched
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("Range")
    fun getNotSeen(userId: Long) : ArrayList<Message?>{
        val database = DbHelper.getInstance(context)
        val bollText = false.toString()

        val selectQuery = "SELECT * FROM $MESSAGE_TABLE_NAME WHERE " +
                " ($MESSAGE_COLUMN_RECEIVER = \"$userId\" AND $MESSAGE_COLUMN_SEEN = \"$bollText\") " +
                "ORDER BY $MESSAGE_COLUMN_TIME DESC"

        val db : SQLiteDatabase = database.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        var messages : ArrayList<Message?> = ArrayList()

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    var id = cursor.getLong(cursor.getColumnIndex(MESSAGE_COLUMN_ID))
                    var sender = cursor.getLong(cursor.getColumnIndex(MESSAGE_COLUMN_SENDER))
                    var receiver = cursor.getLong(cursor.getColumnIndex(MESSAGE_COLUMN_RECEIVER))
                    var text = cursor.getString(cursor.getColumnIndex(MESSAGE_COLUMN_TEXT))
                    var time = cursor.getString(cursor.getColumnIndex(MESSAGE_COLUMN_TIME))

                    var message = Message(id,text,sender,receiver,time)
                    message.seen = true

                    update(message)

                    messages.add(message)

                }while (cursor.moveToNext())
            }
        }

        cursor.close()
        database.close()
        return messages
    }

    //delete all entries
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

    // count how many items are in the table
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

    // update a message given a message
    override fun update(message: Message) {
        val database = DbHelper.getInstance(context)
        val db : SQLiteDatabase = database.writableDatabase
        val updateQuery = "UPDATE $MESSAGE_TABLE_NAME SET $MESSAGE_COLUMN_SEEN = \"${message.seen.toString()}\" WHERE $MESSAGE_COLUMN_ID = \"${message.id}\""

        db.execSQL(updateQuery)
        database.close()
    }

    // insert a message given a message
    @RequiresApi(Build.VERSION_CODES.O)
    override fun insert(message: Message): Long? {
        var id: Long = -1
        val database= DbHelper.getInstance(context)

        try {
            database.writableDatabase.use { db ->
                val content = ContentValues()

                content.put(MESSAGE_COLUMN_SENDER, message.senderId)
                content.put(MESSAGE_COLUMN_RECEIVER, message.receiverId)
                content.put(MESSAGE_COLUMN_TEXT, message.text)
                content.put(MESSAGE_COLUMN_TIME, message.timestamp)
                content.put(MESSAGE_COLUMN_SEEN, message.seen.toString())

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