package com.example.carrental.Logic.proxy

import com.example.carrental.database.model.Car

interface PaymentService {
    fun processPayment(target : Long, car: Car)
}