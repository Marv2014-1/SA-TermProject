package com.example.carrental.database

/**
 * This class allows access to the car table through the database
 */

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.util.Log
import com.example.carrental.database.model.Car
import com.example.carrental.database.model.User

class CarTable(private val context: Context) : DataFunctions <Long , Car> {

    companion object{
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
    }

    override fun getALL(): List<Car> {
        TODO("Not yet implemented")
    }

    fun getCount(): Long{
        val appDbHelper = DbHelper.getInstance(context)
        val db = appDbHelper.writableDatabase
        return DatabaseUtils.queryNumEntries(db, CAR_TABLE_NAME)
    }

    @SuppressLint("Range")
    fun getByOwner(user : User) : ArrayList<Car>{
        val database = DbHelper.getInstance(context)
        val selectQuery = "SELECT * FROM $CAR_TABLE_NAME WHERE $CAR_COLUMN_OWNER = \"${user.id}\""

        val db : SQLiteDatabase = database.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        var cars : ArrayList<Car> = ArrayList()

        if (cursor != null){
            if (cursor.moveToFirst()){
                do{
                    Log.e("Testing", "Start")
                    val id = cursor.getLong(cursor.getColumnIndex(CAR_COLUMN_ID))
                    val owner = cursor.getLong(cursor.getColumnIndex(CAR_COLUMN_OWNER))
                    val model = cursor.getString(cursor.getColumnIndex(CAR_COLUMN_MODEL))
                    val year = cursor.getString(cursor.getColumnIndex(CAR_COLUMN_YEAR))
                    val mileage = cursor.getInt(cursor.getColumnIndex(CAR_COLUMN_MILEAGE))
                    val availability = cursor.getString(cursor.getColumnIndex(CAR_COLUMN_AVAILABILITY))
                    val location = cursor.getString(cursor.getColumnIndex(CAR_COLUMN_LOCATION))
                    val price = cursor.getInt(cursor.getColumnIndex(CAR_COLUMN_PRICE))
                    val renter = cursor.getLong(cursor.getColumnIndex(CAR_COLUMN_RENTER))


                    var car : Car? = Car(id, owner, model, year, mileage, availability, location, price, renter)


                    if (car != null){
                        cars.add(car)
                    }
                } while (cursor.moveToNext())
            }
        }

        database.close()
        cursor.close()
        return cars
    }

    @SuppressLint("Range")
    fun getOthers(user: User) : ArrayList<Car>{
        val database = DbHelper.getInstance(context)
        val selectQuery = "SELECT * FROM $CAR_TABLE_NAME WHERE $CAR_COLUMN_OWNER != \"${user.id}\""

        val db : SQLiteDatabase = database.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        var cars : ArrayList<Car> = ArrayList()

        if (cursor != null){
            if (cursor.moveToFirst()){
                do{
                    Log.e("Testing", "Start")
                    val id = cursor.getLong(cursor.getColumnIndex(CAR_COLUMN_ID))
                    val owner = cursor.getLong(cursor.getColumnIndex(CAR_COLUMN_OWNER))
                    val model = cursor.getString(cursor.getColumnIndex(CAR_COLUMN_MODEL))
                    val year = cursor.getString(cursor.getColumnIndex(CAR_COLUMN_YEAR))
                    val mileage = cursor.getInt(cursor.getColumnIndex(CAR_COLUMN_MILEAGE))
                    val availability = cursor.getString(cursor.getColumnIndex(CAR_COLUMN_AVAILABILITY))
                    val location = cursor.getString(cursor.getColumnIndex(CAR_COLUMN_LOCATION))
                    val price = cursor.getInt(cursor.getColumnIndex(CAR_COLUMN_PRICE))
                    val renter = cursor.getLong(cursor.getColumnIndex(CAR_COLUMN_RENTER))

                    Log.e("renter", renter.toString())

                    val username = findOwner(owner)

                    var car : Car? = Car(id, owner, model, year, mileage, availability, location, price, renter)

                    car?.setUsername(username)

                    if (car != null && renter == -1L){
                        cars.add(car)
                    }
                } while (cursor.moveToNext())
            }
        }

        database.close()
        cursor.close()
        return cars
    }

    fun findOwner(owner : Long) : String{
        val database = DbHelper.getInstance(context)
        val db = UserTable(context)

        val user = db.getByID(owner)

        database.close()
        return user.username!!
    }

    override fun deleteAll(): Boolean {
        val database= DbHelper.getInstance(context)
        var deletedAll = false

        database.writableDatabase.use { db ->
            db.delete(CAR_TABLE_NAME, null, null)

            val count = getCount()

            if (count == 0L)
                deletedAll = true
        }

        return deletedAll
    }

    override fun deleteById(id: Long): Int {
        val database= DbHelper.getInstance(context)
        val db = database.writableDatabase
        val whereClause = "$CAR_COLUMN_ID = ?"
        val deleted = db . delete(CAR_TABLE_NAME, whereClause, arrayOf(id.toString()))
        db.close()
        return deleted
    }

    override fun delete(t: Car) {
        TODO("Not yet implemented")
    }

    override fun update(car: Car) {
        val database = DbHelper.getInstance(context)
        val db : SQLiteDatabase = database.writableDatabase
        val updateQuery = "UPDATE $CAR_TABLE_NAME SET " +
                        " $CAR_COLUMN_MODEL = \"${car.model}\", " +
                        " $CAR_COLUMN_YEAR = \"${car.year}\", " +
                        " $CAR_COLUMN_MILEAGE = \"${car.mileage}\", " +
                        " $CAR_COLUMN_AVAILABILITY = \"${car.availability}\", " +
                        " $CAR_COLUMN_LOCATION = \"${car.location}\", " +
                        " $CAR_COLUMN_PRICE = \"${car.price}\", " +
                        " $CAR_COLUMN_RENTER = \"${car.renter}\" " +
                        "WHERE $CAR_COLUMN_ID = \"${car.id}\" "

        db.execSQL(updateQuery)

        db.close()
        database.close()
    }

    override fun insert(car: Car): Long? {
        var id: Long = -1
        val database= DbHelper.getInstance(context)

        try {
            database.writableDatabase.use { db ->
                val content = ContentValues()

                content.put(CAR_COLUMN_OWNER, car.owner)
                content.put(CAR_COLUMN_MODEL, car.model)
                content.put(CAR_COLUMN_YEAR, car.year)
                content.put(CAR_COLUMN_MILEAGE, car.mileage)
                content.put(CAR_COLUMN_AVAILABILITY, car.availability)
                content.put(CAR_COLUMN_LOCATION, car.location)
                content.put(CAR_COLUMN_PRICE, car.price)
                content.put(CAR_COLUMN_RENTER, car.renter)

                id = db.insert(CAR_TABLE_NAME, null, content)

                car.id = id

            }
        } catch (e : SQLiteException){
            println(e)
        }

        database.close()
        return id
    }

    @SuppressLint("Range")
    override fun getByID(id: Long): Car? {
        val database = DbHelper.getInstance(context)
        val selectQuery = "SELECT * FROM $CAR_TABLE_NAME WHERE $CAR_COLUMN_ID = \"$id\""

        val db : SQLiteDatabase = database.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        var car : Car? = null

        if (cursor != null){
            if (cursor.moveToFirst()){
                do{
                    Log.e("Testing", "Start")
                    val id = cursor.getLong(cursor.getColumnIndex(CAR_COLUMN_ID))
                    val owner = cursor.getLong(cursor.getColumnIndex(CAR_COLUMN_OWNER))
                    val model = cursor.getString(cursor.getColumnIndex(CAR_COLUMN_MODEL))
                    val year = cursor.getString(cursor.getColumnIndex(CAR_COLUMN_YEAR))
                    val mileage = cursor.getInt(cursor.getColumnIndex(CAR_COLUMN_MILEAGE))
                    val availability = cursor.getString(cursor.getColumnIndex(CAR_COLUMN_AVAILABILITY))
                    val location = cursor.getString(cursor.getColumnIndex(CAR_COLUMN_LOCATION))
                    val price = cursor.getInt(cursor.getColumnIndex(CAR_COLUMN_PRICE))
                    val renter = cursor.getLong(cursor.getColumnIndex(CAR_COLUMN_RENTER))


                    car = Car(id, owner, model, year, mileage, availability, location, price, renter)

                } while (cursor.moveToNext())
            }
        }

        database.close()
        cursor.close()
        return car
    }
}