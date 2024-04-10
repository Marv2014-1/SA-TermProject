package com.example.carrental.database

/**
 * This class allows access to the rental table through the database
 */

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import com.example.carrental.database.model.Car
import com.example.carrental.database.model.Message
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
        private val RENTAL_COLUMN_OWNER_SEEN = "owner_seen"
        private val RENTAL_COLUMN_RENTER_SEEN = "renter_seen"

    }

    override fun getALL(): List<Rental> {
        TODO("Not yet implemented")
    }

    override fun update(t: Rental) {
        TODO("Not yet implemented")
    }

    @SuppressLint("Range")
    fun getNotSeen(userId: Long) : ArrayList<Rental?>{
        val database = DbHelper.getInstance(context)
        val bollText = false.toString()

        val selectQuery = "SELECT * FROM $RENTAL_TABLE_NAME WHERE " +
                " ( $RENTAL_COLUMN_OWNER = \"$userId\" AND  $RENTAL_COLUMN_OWNER_SEEN = \"${false.toString()}\") " +
                " OR ( $RENTAL_COLUMN_RENTER  = \"$userId\" AND $RENTAL_COLUMN_RENTER_SEEN = \"${false.toString()}\") "
        val db : SQLiteDatabase = database.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        var rentals : ArrayList<Rental?> = ArrayList()

        if (cursor != null){
            if (cursor.moveToFirst()){
                do{
                    var id = cursor.getLong(cursor.getColumnIndex(RENTAL_COLUMN_ID))
                    var carid = cursor.getLong(cursor.getColumnIndex(RENTAL_COLUMN_CAR))
                    var owner = cursor.getLong(cursor.getColumnIndex(RENTAL_COLUMN_OWNER))
                    var renter = cursor.getLong(cursor.getColumnIndex(RENTAL_COLUMN_RENTER))
                    var price = cursor.getLong(cursor.getColumnIndex(RENTAL_COLUMN_PRICE))
                    var location = cursor.getString(cursor.getColumnIndex(RENTAL_COLUMN_LOCATION))
                    var model = cursor.getString(cursor.getColumnIndex(RENTAL_COLUMN_MODEL))
                    var year = cursor.getString(cursor.getColumnIndex(RENTAL_COLUMN_YEAR))
                    var mileage = cursor.getInt(cursor.getColumnIndex(RENTAL_COLUMN_MILEAGE))
                    var date = cursor.getString(cursor.getColumnIndex(RENTAL_COLUMN_DATE))


                    var rental : Rental? = Rental(id, carid, owner, renter, price, location, model, year, mileage, date)

                    if (rental?.owner == userId){
                        rental.ownerSeen = true
                    }

                    if (rental?.renter == userId){
                        rental.renterSeen = true
                    }

                    update(rental!!, userId)

                    rentals.add(rental)

                } while (cursor.moveToNext())
            }
        }

        cursor.close()
        database.close()
        return rentals
    }

    @SuppressLint("Range")
    fun getAllMine(userId : Long) : ArrayList<Rental>{
        val database = DbHelper.getInstance(context)
        val selectQueryAsOwner = "SELECT * FROM $RENTAL_TABLE_NAME WHERE $RENTAL_COLUMN_OWNER = \"$userId\""

        val db : SQLiteDatabase = database.writableDatabase
        val cursor = db.rawQuery(selectQueryAsOwner, null)

        var rentals : ArrayList<Rental> = ArrayList()

        if (cursor != null){
            if (cursor.moveToFirst()){
                do{
                    var id = cursor.getLong(cursor.getColumnIndex(RENTAL_COLUMN_ID))
                    var carid = cursor.getLong(cursor.getColumnIndex(RENTAL_COLUMN_CAR))
                    var owner = cursor.getLong(cursor.getColumnIndex(RENTAL_COLUMN_OWNER))
                    var renter = cursor.getLong(cursor.getColumnIndex(RENTAL_COLUMN_RENTER))
                    var price = cursor.getLong(cursor.getColumnIndex(RENTAL_COLUMN_PRICE))
                    var location = cursor.getString(cursor.getColumnIndex(RENTAL_COLUMN_LOCATION))
                    var model = cursor.getString(cursor.getColumnIndex(RENTAL_COLUMN_MODEL))
                    var year = cursor.getString(cursor.getColumnIndex(RENTAL_COLUMN_YEAR))
                    var mileage = cursor.getInt(cursor.getColumnIndex(RENTAL_COLUMN_MILEAGE))
                    var date = cursor.getString(cursor.getColumnIndex(RENTAL_COLUMN_DATE))


                    var rental : Rental? = Rental(id, carid, owner, renter, price, location, model, year, mileage, date)


                    if (rental != null){
                        rentals.add(rental)
                    }
                } while (cursor.moveToNext())
            }
        }

        val selectQueryAsRenter = "SELECT * FROM $RENTAL_TABLE_NAME WHERE $RENTAL_COLUMN_RENTER = \"$userId\""
        val cursor2 = db.rawQuery(selectQueryAsRenter, null)

        if (cursor2 != null){
            if (cursor2.moveToFirst()){
                do{
                    var id = cursor2.getLong(cursor2.getColumnIndex(RENTAL_COLUMN_ID))
                    var carid = cursor2.getLong(cursor2.getColumnIndex(RENTAL_COLUMN_CAR))
                    var owner = cursor2.getLong(cursor2.getColumnIndex(RENTAL_COLUMN_OWNER))
                    var renter = cursor2.getLong(cursor2.getColumnIndex(RENTAL_COLUMN_RENTER))
                    var price = cursor2.getLong(cursor2.getColumnIndex(RENTAL_COLUMN_PRICE))
                    var location = cursor2.getString(cursor2.getColumnIndex(RENTAL_COLUMN_LOCATION))
                    var model = cursor2.getString(cursor2.getColumnIndex(RENTAL_COLUMN_MODEL))
                    var year = cursor2.getString(cursor2.getColumnIndex(RENTAL_COLUMN_YEAR))
                    var mileage = cursor2.getInt(cursor2.getColumnIndex(RENTAL_COLUMN_MILEAGE))
                    var date = cursor2.getString(cursor2.getColumnIndex(RENTAL_COLUMN_DATE))


                    var rental : Rental? = Rental(id, carid, owner, renter, price, location, model, year, mileage, date)


                    if (rental != null){
                        rentals.add(rental)
                    }
                } while (cursor2.moveToNext())
            }
        }

        cursor.close()
        cursor2.close()
        database.close()

        return rentals
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

     fun update(rental: Rental, userId: Long) {
        val database = DbHelper.getInstance(context)
        val db : SQLiteDatabase = database.writableDatabase

        var updateQuery : String = ""

        if (userId == rental.owner){
            updateQuery = "UPDATE $RENTAL_TABLE_NAME SET $RENTAL_COLUMN_OWNER_SEEN = \"${rental.ownerSeen.toString()}\" WHERE $RENTAL_COLUMN_ID = \"${rental.id}\""
        }

        if (userId == rental.renter){
            updateQuery = "UPDATE $RENTAL_TABLE_NAME SET $RENTAL_COLUMN_RENTER_SEEN = \"${rental.renterSeen.toString()}\" WHERE $RENTAL_COLUMN_ID = \"${rental.id}\""
        }

        db.execSQL(updateQuery)
        database.close()
    }

    override fun insert(rental: Rental): Long? {
        var id: Long = -1
        val database= DbHelper.getInstance(context)

        try {
            database.writableDatabase.use { db ->
                val content = ContentValues()

                content.put(RENTAL_COLUMN_CAR, rental.car)
                content.put(RENTAL_COLUMN_DATE, rental.date)
                content.put(RENTAL_COLUMN_YEAR, rental.year)
                content.put(RENTAL_COLUMN_MILEAGE, rental.mileage)
                content.put(RENTAL_COLUMN_MODEL, rental.model)
                content.put(RENTAL_COLUMN_LOCATION, rental.location)
                content.put(RENTAL_COLUMN_PRICE, rental.price)
                content.put(RENTAL_COLUMN_RENTER, rental.renter)
                content.put(RENTAL_COLUMN_OWNER, rental.owner)
                content.put(RENTAL_COLUMN_OWNER_SEEN, rental.ownerSeen.toString())
                content.put(RENTAL_COLUMN_RENTER_SEEN, rental.renterSeen.toString())


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