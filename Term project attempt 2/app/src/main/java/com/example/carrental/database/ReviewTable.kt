package com.example.carrental.database

/**
 * This class allows access to the car table through the database
 */

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import com.example.carrental.database.model.Review

class ReviewTable(private val context: Context) : DataFunctions <Long , Review> {

    companion object{
        private val REVIEW_TABLE_NAME = "review"
        private val REVIEW_COLUMN_ID = "id"
        private val REVIEW_COLUMN_REVIEWER = "reviewer"
        private val REVIEW_COLUMN_TARGET = "target"
        private val REVIEW_COLUMN_CONTENT = "content"
        private val REVIEW_COLUMN_SCORE = "score"
    }

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
                    val score = cursor.getInt(cursor.getColumnIndex(REVIEW_COLUMN_SCORE))

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

    override fun deleteAll(): Boolean {
        TODO("Not yet implemented")
    }

    override fun deleteById(id: Long): Int {
        TODO("Not yet implemented")
    }

    override fun delete(t: Review) {
        TODO("Not yet implemented")
    }

    override fun update(t: Review) {
        TODO("Not yet implemented")
    }

    override fun insert(review: Review): Long? {
        var id: Long = -1
        val database= DbHelper.getInstance(context)

        try {
            database.writableDatabase.use { db ->
                val content = ContentValues()

                content.put(REVIEW_COLUMN_REVIEWER, review.reviewer)
                content.put(REVIEW_COLUMN_TARGET, review.target)
                content.put(REVIEW_COLUMN_CONTENT, review.content)
                content.put(REVIEW_COLUMN_SCORE, review.score)

                id = db.insertOrThrow(REVIEW_TABLE_NAME, null, content)

                review.id = id
            }
        } catch (e : SQLiteException){
            println(e)
        }

        database.close()
        return id
    }

    override fun getByID(id: Long): Review? {
        TODO("Not yet implemented")
    }

}