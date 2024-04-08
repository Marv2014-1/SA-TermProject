package com.example.carrental.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.util.Log
import android.widget.Toast
import com.example.carrental.database.model.Car
import com.example.carrental.database.model.User

class UserTable(private val context: Context) : DataFunctions <Long , User> {

    //table and column names
    companion object{
        private const val USER_TABLE_NAME = "user"
        private const val USER_COLUMN_ID = "id"
        private const val USER_COLUMN_USERNAME = "username"
        private const val USER_COLUMN_PASSWORD = "password"
        private const val USER_COLUMN_Q1 = "question1"
        private const val USER_COLUMN_Q2 = "question2"
        private const val USER_COLUMN_Q3 = "question3"
        private const val USER_COLUMN_BALANCE = "balance"
    }

    @SuppressLint("Range")
    override fun getALL(): List<User> {
        val database= DbHelper.getInstance(context)
        val selectQuery = "SELECT * FROM $USER_TABLE_NAME"
        val db : SQLiteDatabase = database.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor != null){
            if (cursor.moveToFirst()){
                val users = ArrayList<User>()
                do {
                    val id = cursor.getLong(cursor.getColumnIndex(USER_COLUMN_ID))
                    val username = cursor.getString(cursor.getColumnIndex(USER_COLUMN_USERNAME))
                    val password = cursor.getString(cursor.getColumnIndex(USER_COLUMN_PASSWORD))
                    val Q1 = cursor.getString(cursor.getColumnIndex(USER_COLUMN_Q1))
                    val Q2 = cursor.getString(cursor.getColumnIndex(USER_COLUMN_Q2))
                    val Q3 = cursor.getString(cursor.getColumnIndex(USER_COLUMN_Q3))
                    val balance = cursor.getLong(cursor.getColumnIndex(USER_COLUMN_BALANCE))

                    var user = User(id, username, password, Q1, Q2, Q3, balance)
                    users.add(user)
                } while (cursor.moveToNext())
                return users
            }
        }

        database.close()
        cursor.close()
        return emptyList()
    }

    override fun update(user: User){
        val database = DbHelper.getInstance(context)
        val db : SQLiteDatabase = database.writableDatabase
        val updateQuery = "UPDATE $USER_TABLE_NAME SET $USER_COLUMN_PASSWORD = \"${user.password}\" WHERE $USER_COLUMN_USERNAME = \"${user.username}\""

        db.execSQL(updateQuery)
        database.close()
    }

    fun updateBalance(user: User){
        val database = DbHelper.getInstance(context)
        val db : SQLiteDatabase = database.writableDatabase
        val updateQuery = "UPDATE $USER_TABLE_NAME SET $USER_COLUMN_BALANCE = \"${user.balance}\" WHERE $USER_COLUMN_USERNAME = \"${user.username}\""

        db.execSQL(updateQuery)
        database.close()
    }

    fun getCount(): Long{
        val appDbHelper = DbHelper.getInstance(context)
        val db = appDbHelper.writableDatabase
        return DatabaseUtils.queryNumEntries(db, USER_TABLE_NAME)
    }

    override fun deleteById(id: Long): Int {
        val database= DbHelper.getInstance(context)
        TODO("Not yet implemented")
    }

    override fun delete(user: User){
        val database= DbHelper.getInstance(context)
        TODO("Not yet implemented")
    }

    override fun deleteAll(): Boolean {
        val database= DbHelper.getInstance(context)
        var deletedAll = false

        database.writableDatabase.use { db ->
            db.delete(USER_TABLE_NAME, null, null)

            val count = getCount()

            if (count == 0L)
                deletedAll = true
        }

        return deletedAll
    }

    //add a user to the database
    override fun insert(user: User): Long? {
        var id: Long = -1
        val database= DbHelper.getInstance(context)

        try {
            database.writableDatabase.use { db ->
                val content = ContentValues()

                content.put(USER_COLUMN_USERNAME, user.username)
                content.put(USER_COLUMN_PASSWORD, user.password)
                content.put(USER_COLUMN_Q1, user.Q1)
                content.put(USER_COLUMN_Q2, user.Q2)
                content.put(USER_COLUMN_Q3, user.Q3)
                content.put(USER_COLUMN_BALANCE, user.balance)

                id = db.insert(USER_TABLE_NAME, null, content)

                user.id = id
            }
        } catch (e : SQLiteException){
            println(e)
        }

        database.close()
        return id
    }

    @SuppressLint("Range")
    override fun getByID(id: Long): User {
        val database = DbHelper.getInstance(context)
        val selectQuery = "SELECT * FROM $USER_TABLE_NAME WHERE $USER_COLUMN_ID = \"$id\""

        val db : SQLiteDatabase = database.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        var user : User? = null

        if (cursor != null){
            if (cursor.moveToFirst()){
                do{
                    val id = cursor.getLong(cursor.getColumnIndex(USER_COLUMN_ID))
                    val username = cursor.getString(cursor.getColumnIndex(USER_COLUMN_USERNAME))
                    val password = cursor.getString(cursor.getColumnIndex(USER_COLUMN_PASSWORD))
                    val Q1 = cursor.getString(cursor.getColumnIndex(USER_COLUMN_Q1))
                    val Q2 = cursor.getString(cursor.getColumnIndex(USER_COLUMN_Q2))
                    val Q3 = cursor.getString(cursor.getColumnIndex(USER_COLUMN_Q3))
                    val balance = cursor.getLong(cursor.getColumnIndex(USER_COLUMN_BALANCE))

                    user = User(id, username, password, Q1, Q2, Q3, balance)
                } while (cursor.moveToNext())
            }
        }

        database.close()
        cursor.close()
        return user!!
    }

    // get a user given the username
     @SuppressLint("Range")
     fun getByUsernamePassword(username: String, password: String): User? {
        val database = DbHelper.getInstance(context)
        val selectQuery = "SELECT * FROM $USER_TABLE_NAME WHERE $USER_COLUMN_USERNAME = \"$username\"" +
                            " AND $USER_COLUMN_PASSWORD = \"$password\""

        val db : SQLiteDatabase = database.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        var user : User? = null

        if (cursor != null){
            if (cursor.moveToFirst()){
                val id = cursor.getLong(cursor.getColumnIndex(USER_COLUMN_ID))
                val username = cursor.getString(cursor.getColumnIndex(USER_COLUMN_USERNAME))
                val password = cursor.getString(cursor.getColumnIndex(USER_COLUMN_PASSWORD))
                val Q1 = cursor.getString(cursor.getColumnIndex(USER_COLUMN_Q1))
                val Q2 = cursor.getString(cursor.getColumnIndex(USER_COLUMN_Q2))
                val Q3 = cursor.getString(cursor.getColumnIndex(USER_COLUMN_Q3))
                val balance = cursor.getLong(cursor.getColumnIndex(USER_COLUMN_BALANCE))

                user = User(id, username, password, Q1, Q2, Q3, balance)
            }
        }

        database.close()
        cursor.close()
        return user
    }

    // get a user given the username
    @SuppressLint("Range")
    fun getByUsername(username: String): User? {
        val database = DbHelper.getInstance(context)
        val selectQuery = "SELECT * FROM $USER_TABLE_NAME WHERE $USER_COLUMN_USERNAME = \"$username\""

        val db : SQLiteDatabase = database.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        var user : User? = null

        if (cursor != null){
            if (cursor.moveToFirst()){
                val username = cursor.getString(cursor.getColumnIndex(USER_COLUMN_USERNAME))
                val Q1 = cursor.getString(cursor.getColumnIndex(USER_COLUMN_Q1))
                val Q2 = cursor.getString(cursor.getColumnIndex(USER_COLUMN_Q2))
                val Q3 = cursor.getString(cursor.getColumnIndex(USER_COLUMN_Q3))

                user = User(username, Q1, Q2, Q3)
            }
        }

        database.close()
        cursor.close()
        return user
    }
}