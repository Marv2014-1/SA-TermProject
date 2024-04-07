package com.example.carrental.database

import com.example.carrental.database.model.Review
import com.example.carrental.database.model.User

/**
 * This class is an interface that outlines common CRUD operations that may be used as needed
 */

interface DataFunctions <ID, T> {

    fun getALL(): List<T>

    fun getByID(id: ID) : T?

    fun insert(t: T) : Long?

    fun update(t : T)

    fun delete(t: T)

    fun deleteById(id: ID): Int

    fun deleteAll() : Boolean
}