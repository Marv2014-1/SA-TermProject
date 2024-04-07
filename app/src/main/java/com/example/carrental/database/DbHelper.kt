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

private val DATABASE_NAME = "car_rental"

val USER_TABLE_NAME = "user"
val USER_COLUMN_ID = "id"
val USER_COLUMN_USERNAME = "username"
val USER_COLUMN_PASSWORD = "password"
val USER_COLUMN_Q1 = "question1"
val USER_COLUMN_Q2 = "question2"
val USER_COLUMN_Q3 = "question3"
val USER_COLUMN_BALANCE = "balance"

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

private val REVIEW_TABLE_NAME = "review"
private val REVIEW_COLUMN_ID = "id"
private val REVIEW_COLUMN_REVIEWER = "reviewer"
private val REVIEW_COLUMN_TARGET = "target"
private val REVIEW_COLUMN_CONTENT = "content"
private val REVIEW_COLUMN_SCORE = "score"


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
    }

    //When this object is created, it will make all of the tables and columns of said tables
    override fun onCreate(db: SQLiteDatabase) {
        deleteAll()
        recreate()
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
        val userTable = UserTable(context)
        Log.e("user", userTable.deleteAll().toString())
        val reviewTable = ReviewTable(context)
        Log.e("review", reviewTable.deleteAll().toString())
        val rentalTable = RentalTable(context)
        Log.e("rental", rentalTable.deleteAll().toString())
        val carTable = CarTable(context)
        Log.e("car", carTable.deleteAll().toString())
    }

    //Create the database and tables
    private fun recreate(){
        val db:SQLiteDatabase = this.writableDatabase

        val createUser = buildString {
            append("CREATE TABLE IF EXISTS\"$USER_TABLE_NAME\" (\n")
            append("\t\"$USER_COLUMN_ID\"\tINTEGER NOT NULL,\n")
            append("\t\"$USER_COLUMN_USERNAME\"\tTEXT NOT NULL UNIQUE,\n")
            append("\t\"$USER_COLUMN_PASSWORD\"\tTEXT NOT NULL,\n")
            append("\t\"$USER_COLUMN_Q1\"\tTEXT NOT NULL,\n")
            append("\t\"$USER_COLUMN_Q2\"\tTEXT NOT NULL,\n")
            append("\t\"$USER_COLUMN_Q3\"\tTEXT NOT NULL,\n")
            append("\t\"$USER_COLUMN_BALANCE\"\tINTEGER NOT NULL,\n")
            append("\tPRIMARY KEY(\"$USER_COLUMN_ID\" AUTOINCREMENT)\n")
            append(");\n")
        }

        val createCar = buildString {
            append("CREATE TABLE IF EXISTS\"$CAR_TABLE_NAME\" (\n")
            append("\t\"$CAR_COLUMN_ID\"\tINTEGER NOT NULL UNIQUE,\n")
            append("\t\"$CAR_COLUMN_OWNER\"\tINTEGER NOT NULL,\n")
            append("\t\"$CAR_COLUMN_MODEL\"\tTEXT,\n")
            append("\t\"$CAR_COLUMN_YEAR\"\tTEXT,\n")
            append("\t\"$CAR_COLUMN_MILEAGE\"\tINTEGER,\n")
            append("\t\"$CAR_COLUMN_AVAILABILITY\"\tTEXT,\n")
            append("\t\"$CAR_COLUMN_LOCATION\"\tTEXT,\n")
            append("\t\"$CAR_COLUMN_PRICE\"\tINTEGER,\n")
            append("\t\"$CAR_COLUMN_RENTER\"\tINTEGER,\n")
            append("\tPRIMARY KEY(\"$CAR_COLUMN_ID\" AUTOINCREMENT),\n")
            append("\tFOREIGN KEY(\"$CAR_COLUMN_OWNER\") REFERENCES \"$USER_TABLE_NAME\"(\"$USER_COLUMN_ID\"),\n")
            append("\tFOREIGN KEY(\"$CAR_COLUMN_RENTER\") REFERENCES \"$USER_TABLE_NAME\"(\"$USER_COLUMN_ID\"),\n")
            append(");\n")
        }

        val createReview = buildString {
            append("CREATE TABLE IF EXISTS\"$REVIEW_TABLE_NAME\" (\n")
            append("\t\"$REVIEW_COLUMN_ID\"\tINTEGER NOT NULL UNIQUE,\n")
            append("\t\"$REVIEW_COLUMN_REVIEWER\"\tINTEGER NOT NULL,\n")
            append("\t\"$REVIEW_COLUMN_TARGET\"\tINTEGER NOT NULL,\n")
            append("\t\"$REVIEW_COLUMN_CONTENT\"\tTEXT,\n")
            append("\t\"$REVIEW_COLUMN_SCORE\"\tINTEGER NOT NULL,\n")
            append("\tPRIMARY KEY(\"$REVIEW_COLUMN_ID\" AUTOINCREMENT),\n")
            append("\tFOREIGN KEY(\"$REVIEW_COLUMN_REVIEWER\") REFERENCES \"$USER_TABLE_NAME\"(\"$USER_COLUMN_ID\"),\n")
            append("\tFOREIGN KEY(\"$REVIEW_COLUMN_TARGET\") REFERENCES \"$USER_TABLE_NAME\"(\"$USER_COLUMN_ID\"),\n")
            append(");\n")
        }

        val createRental = buildString {
            append("CREATE TABLE IF EXISTS\"$RENTAL_TABLE_NAME\" (\n")
            append("\t\"$RENTAL_COLUMN_ID\"\tINTEGER NOT NULL UNIQUE,\n")
            append("\t\"$RENTAL_COLUMN_CAR\"\tINTEGER NOT NULL,\n")
            append("\t\"$RENTAL_COLUMN_OWNER\"\tINTEGER NOT NULL,\n")
            append("\t\"$RENTAL_COLUMN_RENTER\"\tINTEGER NOT NULL,\n")
            append("\t\"$RENTAL_COLUMN_PRICE\"\tINTEGER NOT NULL,\n")
            append("\t\"$RENTAL_COLUMN_LOCATION\"\tTEXT,\n")
            append("\t\"$RENTAL_COLUMN_MODEL\"\tTEXT,\n")
            append("\t\"$RENTAL_COLUMN_YEAR\"\tTEXT,\n")
            append("\t\"$RENTAL_COLUMN_MILEAGE\"\tINTEGER,\n")
            append("\t\"$RENTAL_COLUMN_DATE\"\tTEXT,\n")
            append("\tPRIMARY KEY(\"$RENTAL_COLUMN_ID\" AUTOINCREMENT),\n")
            append("\tFOREIGN KEY(\"$RENTAL_COLUMN_CAR\") REFERENCES \"$CAR_TABLE_NAME\"(\"$CAR_COLUMN_ID\"),\n")
            append("\tFOREIGN KEY(\"$RENTAL_COLUMN_RENTER\") REFERENCES \"$USER_TABLE_NAME\"(\"$USER_COLUMN_ID\"),\n")
            append("\tFOREIGN KEY(\"$RENTAL_COLUMN_OWNER\") REFERENCES \"$USER_TABLE_NAME\"(\"$USER_COLUMN_ID\"),\n")
            append(");\n")
        }

        db.execSQL(createUser)
        db.execSQL(createCar)
        db.execSQL(createReview)
        db.execSQL(createRental)
    }
}