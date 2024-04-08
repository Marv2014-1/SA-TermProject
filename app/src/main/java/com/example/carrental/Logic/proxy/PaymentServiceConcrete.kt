package com.example.carrental.Logic.proxy

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.carrental.Logic.Singleton.Session
import com.example.carrental.database.CarTable
import com.example.carrental.database.RentalTable
import com.example.carrental.database.UserTable
import com.example.carrental.database.model.Car
import com.example.carrental.database.model.Rental
import java.time.LocalTime

class PaymentServiceConcrete(private val context: Context) : PaymentService {
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
        rental.date = LocalTime.now().toString()
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