package com.example.carrental.Logic.ChainOfResponsability

import com.example.carrental.database.model.User

abstract class QuestionHandler {
    protected var successor : QuestionHandler? = null

    public fun setNext(successor : QuestionHandler){
        this.successor = successor
    }

    abstract fun handleRequest(user : User, Q1 : String, Q2 : String, Q3 : String)
}