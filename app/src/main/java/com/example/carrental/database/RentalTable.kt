package com.example.carrental.database

/**
 * This class allows access to the rental table through the database
 */

import android.content.ContentValues
import android.content.Context
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteException
import com.example.carrental.database.model.Rental

class RentalTable(private val context: Context) : DataFunctions <Long , Rental> {

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

    override fun getALL(): List<Rental> {
        TODO("Not yet implemented")
    }

    fun getAllMine(){
        
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

    override fun delete(t: Rental) {
        TODO("Not yet implemented")
    }

    override fun update(t: Rental) {
        TODO("Not yet implemented")
    }

    override fun insert(rental: Rental): Long? {
        var id: Long = -1
        val database= DbHelper.getInstance(context)

        try {
            database.writableDatabase.use { db ->
                val content = ContentValues()

                content.put(RENTAL_COLUMN_DATE, rental.date)
                content.put(RENTAL_COLUMN_YEAR, rental.year)
                content.put(RENTAL_COLUMN_MILEAGE, rental.mileage)
                content.put(RENTAL_COLUMN_MODEL, rental.model)
                content.put(RENTAL_COLUMN_LOCATION, rental.location)
                content.put(RENTAL_COLUMN_PRICE, rental.price)
                content.put(RENTAL_COLUMN_RENTER, rental.renter)
                content.put(RENTAL_COLUMN_OWNER, rental.owner)


                id = db.insert(RENTAL_TABLE_NAME, null, content)

                rental.id = id
            }
        } catch (e : SQLiteException){
            println(e)
        }

        database.close()
        return id
    }

    override fun getByID(id: Long): Rental? {
        TODO("Not yet implemented")
    }
}