package com.example.carrental.Logic.Proxy

import com.example.carrental.database.model.Car

interface PaymentService {
    fun processPayment(target : Long, car: Car)
}