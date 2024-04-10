package com.example.carrental.Logic.proxy

import android.content.Context
import com.example.carrental.Logic.Mediator.Mediator
import com.example.carrental.Logic.Singleton.Session
import com.example.carrental.database.model.Car

class PaymentProxy (private val context: Context ,private val paymentService: PaymentService) : PaymentService {
    override fun processPayment( target: Long, car : Car) {
        val session = Session.getInstance()
        val user = session.getUser()

        if (user.balance!! >= car.price){
            paymentService.processPayment(target, car)
        } else {
            Mediator.hasMoney(false)
        }
    }
}