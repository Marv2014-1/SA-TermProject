package com.example.carrental.database

/**
 * This class allows access to the rental table through the database
 */

import android.content.Context
import android.database.DatabaseUtils
import com.example.carrental.database.model.User

class RentalTable(private val context: Context) : DataFunctions <Long , User> {

    companion object{
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
    }

    override fun getALL(): List<User> {
        TODO("Not yet implemented")
    }

    fun getCount(): Long{
        val appDbHelper = DbHelper.getInstance(context)
        val db = appDbHelper.writableDatabase
        return DatabaseUtils.queryNumEntries(db, RENTAL_TABLE_NAME)
    }

    override fun deleteAll(): Boolean {
        val database= DbHelper.getInstance(context)
        var deletedAll = false

        database.writableDatabase.use { db ->
            db.delete(RENTAL_TABLE_NAME, null, null)

            val count = getCount()

            if (count == 0L)
                deletedAll = true
        }

        return deletedAll
    }

    override fun deleteById(id: Long): Int {
        TODO("Not yet implemented")
    }

    override fun delete(t: User) {
        TODO("Not yet implemented")
    }

    override fun update(t: User) {
        TODO("Not yet implemented")
    }

    override fun insert(t: User): Long? {
        TODO("Not yet implemented")
    }

    override fun getByID(id: Long): User? {
        TODO("Not yet implemented")
    }
}