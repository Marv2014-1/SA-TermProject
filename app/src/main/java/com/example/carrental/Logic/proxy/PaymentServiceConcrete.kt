package com.example.carrental.Logic.proxy

/**
 * this is the concrete payment class
 */

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.carrental.Logic.Singleton.Session
import com.example.carrental.database.CarTable
import com.example.carrental.database.RentalTable
import com.example.carrental.database.UserTable
import com.example.carrental.database.model.Car
import com.example.carrental.database.model.Rental
import java.time.LocalDate
import java.time.LocalTime

class PaymentServiceConcrete(private val context: Context) : PaymentService {
    // Will make a call to the database once it is ensured that the user has the funds
    @RequiresApi(Build.VERSION_CODES.O)
    override fun processPayment(target: Long, car: Car) {
        val session = Session.getInstance()
        val user = session.getUser()

        user.balance = user.balance!! - car.price

        val userTable = UserTable(context)
        var targetUser = userTable.getByID(target)
        targetUser.balance = targetUser.balance!! + car.price

        userTable.updateBalance(user)
        userTable.updateBalance(targetUser)

        car.renter = target

        val carTable = CarTable(context)
        carTable.update(car)

        val rentalTable = RentalTable(context)
        val rental = Rental()
        rental.date = LocalDate.now().toString() + " " + LocalTime.now().hour + ":" + LocalTime.now().minute
        rental.year = car.year
        rental.mileage = car.mileage
        rental.model = car.model
        rental.location = car.location
        rental.price = car.price.toLong()
        rental.renter = user.id
        rental.owner = target

        rentalTable.insert(rental)
    }
}