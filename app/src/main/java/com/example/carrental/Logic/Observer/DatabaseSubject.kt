package com.example.carrental.Logic.Observer

/**
 * this class is the subject that will be sending notifications to the users
 */

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.carrental.Logic.Mediator.Mediator
import com.example.carrental.database.MessageTable
import com.example.carrental.database.RentalTable
import com.example.carrental.database.ReviewTable
import com.example.carrental.database.model.Message
import com.example.carrental.database.model.Rental
import com.example.carrental.database.model.Review

class DatabaseSubject {
    private val observers = mutableListOf<DatabaseObserver>()
    private lateinit var unseenMessages : ArrayList <Message?>
    private lateinit var unseenRentals : ArrayList <Rental?>
    private lateinit var unseenReviews : ArrayList <Review?>

    //search for updates within the databse
    @RequiresApi(Build.VERSION_CODES.O)
    fun searchDatabaseForUpdates(context: Context, userId : Long){

        unseenMessages = ArrayList <Message?>()
        unseenRentals = ArrayList <Rental?>()
        unseenReviews = ArrayList <Review?>()

        val messages = MessageTable(context)
        val rentals = RentalTable(context)
        val reviews = ReviewTable(context)

        unseenMessages = messages.getNotSeen(userId)
        unseenRentals = rentals.getNotSeen(userId)
        unseenReviews = reviews.getNotSeen(userId)

        if (!unseenMessages.isEmpty()){
            if (messages != null) {
                for (message in unseenMessages) {
                    val senderName = Mediator.getUserName(context, message!!.senderId)
                    val text = "User $senderName has sent you a message!"
                    notifyObservers(text)
                }
            }
        }

        //rental notifications not working
//        if (!unseenRentals.isEmpty()){
//            for (rental in unseenRentals){
//                if (rental != null) {
//                    var text = ""
//
//                    val curUser = Mediator.getUserName(context, userId)
//
//                    if (userId == rental?.owner) {
//                        val other = Mediator.getUserName(context, rental.renter!!)
//                        text = "$other rented a car from you!"
//                    }
//
//                    if (userId == rental?.renter) {
//                        val other = Mediator.getUserName(context, rental.owner!!)
//                        text = "You rented ${other}'s car!"
//                    }
//
//                    notifyObservers(text)
//                }
//            }
//        }

        if (!unseenReviews.isEmpty()){
            for (review in unseenReviews){
                if (review != null) {
                    val senderName = Mediator.getUserName(context, review.reviewer!!)
                    val text = "User $senderName has given you a score of ${review.score}"
                    notifyObservers(text)
                }
            }
        }
    }

    //register an observer to be messaged
    fun registerObserver(observer: DatabaseObserver) {
        observers.add(observer)
    }

    //delete an observer
    fun unregisterObserver(observer: DatabaseObserver) {
        Mediator.resetSession()
        observers.remove(observer)
    }

    //send messages to all
    private fun notifyObservers(item: String) {
        Log.e("Item", item)
        for (observer in observers) {
            observer.onDatabaseUpdated(item)
        }
    }



}