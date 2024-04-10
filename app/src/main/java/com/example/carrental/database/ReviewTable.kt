package com.example.carrental.database

/**
 * This class allows access to the car table through the database
 */

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.util.Log
import com.example.carrental.database.model.Review
import com.example.carrental.database.model.User

class ReviewTable(private val context: Context) : DataFunctions <Long , Review> {

    companion object{
        private val REVIEW_TABLE_NAME = "review"
        private val REVIEW_COLUMN_ID = "id"
        private val REVIEW_COLUMN_REVIEWER = "reviewer"
        private val REVIEW_COLUMN_TARGET = "target"
        private val REVIEW_COLUMN_CONTENT = "content"
        private val REVIEW_COLUMN_SCORE = "score"
        private val REVIEW_COLUMN_SEEN = "seen"
    }

    // get all reviews
    @SuppressLint("Range")
    override fun getALL(): List<Review> {
        val database= DbHelper.getInstance(context)
        val selectQuery = "SELECT * FROM $REVIEW_TABLE_NAME"
        val db : SQLiteDatabase = database.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor != null){
            if (cursor.moveToFirst()){
                val reviews = ArrayList<Review>()
                do {
                    val id = cursor.getLong(cursor.getColumnIndex(REVIEW_COLUMN_ID))
                    val reviewer = cursor.getLong(cursor.getColumnIndex(REVIEW_COLUMN_ID))
                    val target = cursor.getLong(cursor.getColumnIndex(REVIEW_COLUMN_REVIEWER))
                    val content = cursor.getString(cursor.getColumnIndex(REVIEW_COLUMN_CONTENT))
                    val score = cursor.getDouble(cursor.getColumnIndex(REVIEW_COLUMN_SCORE))

                    var review = Review(id, reviewer, target, content, score)

                    reviews.add(review)
                } while (cursor.moveToNext())
                return reviews
            }
        }

        database.close()
        cursor.close()
        return emptyList()
    }

    // count how many items are in the table
    fun getCount(): Long{
        val appDbHelper = DbHelper.getInstance(context)
        val db = appDbHelper.writableDatabase
        return DatabaseUtils.queryNumEntries(db, REVIEW_TABLE_NAME)
    }

    //delete all entries
    override fun deleteAll(): Boolean {
        val database= DbHelper.getInstance(context)
        var deletedAll = false

        database.writableDatabase.use { db ->
            db.delete(REVIEW_TABLE_NAME, null, null)

            val count = getCount()

            if (count == 0L)
                deletedAll = true
        }

        return deletedAll
    }

    override fun deleteById(id: Long): Int {
        TODO("Not yet implemented")
    }

    override fun delete(t: Review) {
        TODO("Not yet implemented")
    }

    // update a review given a review
    override fun update(review: Review) {
        val database = DbHelper.getInstance(context)
        val db : SQLiteDatabase = database.writableDatabase
        val updateQuery = "UPDATE $REVIEW_TABLE_NAME SET $REVIEW_COLUMN_SEEN = \"${review.seen.toString()}\" WHERE $REVIEW_COLUMN_ID = \"${review.id}\""

        db.execSQL(updateQuery)
        database.close()
    }

    // insert a review given a review
    override fun insert(review: Review): Long? {
        var id: Long = -1
        val database= DbHelper.getInstance(context)

        try {
            database.writableDatabase.use { db ->
                val content = ContentValues()

                content.put(REVIEW_COLUMN_REVIEWER, review.reviewer)
                content.put(REVIEW_COLUMN_TARGET, review.target)
//                content.put(REVIEW_COLUMN_CONTENT, review.content)
                content.put(REVIEW_COLUMN_SCORE, review.score)
                content.put(REVIEW_COLUMN_SEEN, review.seen.toString())

                id = db.insertOrThrow(REVIEW_TABLE_NAME, null, content)

                review.id = id
            }
        } catch (e : SQLiteException){
            println(e)
        }

        database.close()
        return id
    }

    // get a review by the given id
    @SuppressLint("Range")
    override fun getByID(id: Long): Review? {
        val database = DbHelper.getInstance(context)
        val selectQuery = "SELECT * FROM $REVIEW_TABLE_NAME WHERE $REVIEW_COLUMN_TARGET = \"$id\""

        val db : SQLiteDatabase = database.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        var review : Review? = null

        var total = 0.0
        var outOF = 0.0

        review = Review()

        if (cursor != null){
            if (cursor.moveToFirst()){
                do{
                    total += cursor.getLong(cursor.getColumnIndex(REVIEW_COLUMN_SCORE))
                    outOF += 5

                } while (cursor.moveToNext())
            }
        }

        review.setScore(total/outOF)

        database.close()
        cursor.close()
        return review
    }

    // if a review has not been seen it will be returned
    @SuppressLint("Range")
    fun getNotSeen(userId: Long) : ArrayList<Review?>{
        val database= DbHelper.getInstance(context)
        val bollText = false.toString()
        val selectQuery = "SELECT * FROM $REVIEW_TABLE_NAME WHERE" +
                " ( $REVIEW_COLUMN_TARGET = \"$userId\" AND $REVIEW_COLUMN_SEEN = \"$bollText\") "

        val db : SQLiteDatabase = database.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        Log.e("cursor" , cursor.count.toString())

        val reviews = ArrayList<Review?>()
        if (cursor != null){
            if (cursor.moveToFirst()){
                do {
                    val id = cursor.getLong(cursor.getColumnIndex(REVIEW_COLUMN_ID))
                    val reviewer = cursor.getLong(cursor.getColumnIndex(REVIEW_COLUMN_ID))
                    val target = cursor.getLong(cursor.getColumnIndex(REVIEW_COLUMN_REVIEWER))
//                    val content = cursor.getString(cursor.getColumnIndex(REVIEW_COLUMN_CONTENT))
                    val score = cursor.getDouble(cursor.getColumnIndex(REVIEW_COLUMN_SCORE))

                    var review : Review? = Review(id, reviewer, target, score)
                    review?.seen = true

                    if (review != null) {
                        update(review)
                    }

                    reviews.add(review)
                } while (cursor.moveToNext())

            }
        }

        database.close()
        cursor.close()
        return reviews
    }

}