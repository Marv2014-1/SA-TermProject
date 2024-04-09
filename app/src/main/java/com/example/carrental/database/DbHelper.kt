package com.example.carrental.database

/**
 * this class allows for the connection to the database through a singleton instance
 */

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
// Database name
private val DATABASE_NAME = "car_rental"

// User table variables
private val USER_TABLE_NAME = "user"
private val USER_COLUMN_ID = "id"
private val USER_COLUMN_USERNAME = "username"
private val USER_COLUMN_PASSWORD = "password"
private val USER_COLUMN_Q1 = "question1"
private val USER_COLUMN_Q2 = "question2"
private val USER_COLUMN_Q3 = "question3"
private val USER_COLUMN_BALANCE = "balance"

// Car table variables
private val CAR_TABLE_NAME = "car"
private val CAR_COLUMN_ID = "id"
private val CAR_COLUMN_OWNER = "owner"
private val CAR_COLUMN_MODEL = "model"
private val CAR_COLUMN_YEAR = "year"
private val CAR_COLUMN_MILEAGE = "mileage"
private val CAR_COLUMN_AVAILABILITY = "availability"
private val CAR_COLUMN_LOCATION = "location"
private val CAR_COLUMN_PRICE = "price"
private val CAR_COLUMN_RENTER = "renter"

// Review table variables
private val REVIEW_TABLE_NAME = "review"
private val REVIEW_COLUMN_ID = "id"
private val REVIEW_COLUMN_REVIEWER = "reviewer"
private val REVIEW_COLUMN_TARGET = "target"
private val REVIEW_COLUMN_CONTENT = "content"
private val REVIEW_COLUMN_SCORE = "score"

// Rental table variables
private val RENTAL_TABLE_NAME = "rental"
private val RENTAL_COLUMN_ID = "id"
private val RENTAL_COLUMN_CAR = "car"
private val RENTAL_COLUMN_OWNER = "owner"
private val RENTAL_COLUMN_RENTER = "renter"
private val RENTAL_COLUMN_PRICE = "price"
private val RENTAL_COLUMN_LOCATION = "location"
private val RENTAL_COLUMN_MODEL = "model"
private val RENTAL_COLUMN_YEAR = "year"
private val RENTAL_COLUMN_MILEAGE = "mileage"
private val RENTAL_COLUMN_DATE = "date"

// Message table variables
private val MESSAGE_TABLE_NAME = "messages"
private val MESSAGE_TABLE_ID = "id"
private val MESSAGE_TABLE_SENDER = "sender"
private val MESSAGE_TABLE_RECEIVER = "receiver"
private val MESSAGE_TABLE_TEXT = "text"
private val MESSAGE_TABLE_TIME = "time"

/**
 * Create a helper object to create and access the database (ensure that it is closed after use)
 */
class DbHelper private constructor(private var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME,null, 2) {
    //create  singleton of this class and return a reference to it
    companion object{
        private var database : DbHelper? = null

        private val DATABASE_NAME = "car_rental"

        @Synchronized
        fun getInstance(context: Context) : DbHelper {
            if (database == null){
                database = DbHelper(context)
            }
            return database as DbHelper
        }

        // Create the user table
        private const val SQL_CREATE_USER_TABLE = """
            CREATE TABLE IF NOT EXISTS "user" (
                "id" INTEGER NOT NULL UNIQUE,
                "username" TEXT NOT NULL UNIQUE,
                "password" TEXT NOT NULL,
                "question1" TEXT NOT NULL,
                "question2" TEXT NOT NULL,
                "question3" TEXT NOT NULL,
                "balance" INTEGER NOT NULL,
                PRIMARY KEY("id" AUTOINCREMENT)
            )
        """

        //create the car table
        private const val SQL_CREATE_CAR_TABLE = """
            CREATE TABLE IF NOT EXISTS "car" (
                "id" INTEGER NOT NULL UNIQUE,
                "owner" INTEGER NOT NULL,
                "model" TEXT,
                "year" INTEGER,
                "mileage" INTEGER,
                "availability" TEXT,
                "location" TEXT,
                "price" INTEGER,
                "renter" INTEGER,
                FOREIGN KEY("owner") REFERENCES "user"("id"),
                FOREIGN KEY("renter") REFERENCES "user"("id"),
                PRIMARY KEY("id" AUTOINCREMENT)
            )
        """

        // Create the rental table
        private const val SQL_CREATE_RENTAL_TABLE = """
            CREATE TABLE IF NOT EXISTS "rental" (
                "id" INTEGER NOT NULL UNIQUE,
                "car" INTEGER NOT NULL,
                "owner" INTEGER NOT NULL,
                "renter" INTEGER NOT NULL,
                "price" INTEGER NOT NULL,
                "location" TEXT,
                "model" INTEGER,
                "year" INTEGER,
                "mileage" INTEGER,
                "date" INTEGER,
                FOREIGN KEY("car") REFERENCES "car"("id"),
                FOREIGN KEY("renter") REFERENCES "user"("id"),
                FOREIGN KEY("owner") REFERENCES "user"("id"),
                PRIMARY KEY("id" AUTOINCREMENT)
            )
        """

        // create the review table
        private const val SQL_CREATE_REVIEW_TABLE = """
            CREATE TABLE IF NOT EXISTS "review" (
                "id" INTEGER NOT NULL UNIQUE,
                "reviewer" INTEGER NOT NULL,
                "target" INTEGER NOT NULL,
                "content" INTEGER,
                "score" INTEGER NOT NULL,
                FOREIGN KEY("reviewer") REFERENCES "user"("id"),
                FOREIGN KEY("target") REFERENCES "user"("id"),
                PRIMARY KEY("id" AUTOINCREMENT)
            )
        """

        //create the messages table
        private const val SQL_CREATE_MESSAGE_TABLE = """
                CREATE TABLE IF NOT EXISTS "messages" (
                "id"	INTEGER NOT NULL UNIQUE,
                "sender"	INTEGER NOT NULL,
                "receiver"	INTEGER NOT NULL,
                "text"	TEXT NOT NULL,
                "time"	TEXT NOT NULL,
                FOREIGN KEY("receiver") REFERENCES "user"("id"),
                FOREIGN KEY("sender") REFERENCES "user"("id"),
                PRIMARY KEY("id" AUTOINCREMENT)
            )
        """
    }

    //When this object is created, it will make all of the tables and columns of said tables
    override fun onCreate(db: SQLiteDatabase) {
        // Create tables
        db.execSQL(SQL_CREATE_USER_TABLE)
        db.execSQL(SQL_CREATE_RENTAL_TABLE)
        db.execSQL(SQL_CREATE_REVIEW_TABLE)
        db.execSQL(SQL_CREATE_CAR_TABLE)
        db.execSQL(SQL_CREATE_MESSAGE_TABLE)
    }

    //If the database version changes, we delete the table and upgrade the table
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        deleteAll()

        if (p0 != null) {
            onCreate(p0)
        }
    }

    //delete the database and all of it's tables
    fun deleteAll() {
        this.writableDatabase.execSQL("DROP TABLE IF EXISTS user")
        this.writableDatabase.execSQL("DROP TABLE IF EXISTS car")
        this.writableDatabase.execSQL("DROP TABLE IF EXISTS rental")
        this.writableDatabase.execSQL("DROP TABLE IF EXISTS review")
        this.writableDatabase.execSQL("DROP TABLE IF EXISTS messages")

        onCreate(this.writableDatabase)

        Log.e("DELETION", "Starting to delete the tables")
        val userTable = UserTable(context)
        Log.e("user", userTable.deleteAll().toString())
        val reviewTable = ReviewTable(context)
        Log.e("review", reviewTable.deleteAll().toString())
        val rentalTable = RentalTable(context)
        Log.e("rental", rentalTable.deleteAll().toString())
        val carTable = CarTable(context)
        Log.e("car", carTable.deleteAll().toString())
        val messageTable = MessageTable(context)
        Log.e("car", messageTable.deleteAll().toString())
    }

}