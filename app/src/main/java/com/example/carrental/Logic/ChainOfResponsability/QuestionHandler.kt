package com.example.carrental.Logic.ChainOfResponsability

/**
 * this is the chain of responsibility interface
 */

import com.example.carrental.database.model.User

abstract class QuestionHandler {
    protected var successor : QuestionHandler? = null

    //set the next chain
    public fun setNext(successor : QuestionHandler){
        this.successor = successor
    }

    //handle a request
    abstract fun handleRequest(user : User, Q1 : String, Q2 : String, Q3 : String)
}