package com.example.carrental.database

/**
 * This class allows access to the car table through the database
 */

import android.content.Context
import com.example.carrental.database.model.User

class CarTable(private val context: Context) : DataFunctions <Long , User> {

    companion object{
        private val CAR_TABLE_NAME = "car"
        private val CAR_COLUMN_ID = "id"
        private val CAR_COLUMN_OWNER = "owner"
        private val CAR_COLUMN_MODEL = "model"
        private val CAR_COLUMN_YEAR = "year"
        private val CAR_COLUMN_MILEAGE = "mileage"
        private val CAR_COLUMN_ABAILABILITY = "availability"
        private val CAR_COLUMN_LOCATION = "location"
        private val CAR_COLUMN_PRICE = "price"
        private val CAR_COLUMN_RENTER = "renter"
    }

    override fun getALL(): List<User> {
        TODO("Not yet implemented")
    }

    override fun deleteAll(): Boolean {
        TODO("Not yet implemented")
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