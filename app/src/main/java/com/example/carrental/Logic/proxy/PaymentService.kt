package com.example.carrental.Logic.proxy

/**
 * this is the proxy interface
 */

import com.example.carrental.database.model.Car

interface PaymentService {
    // payment processing done
    fun processPayment(target : Long, car: Car)
}