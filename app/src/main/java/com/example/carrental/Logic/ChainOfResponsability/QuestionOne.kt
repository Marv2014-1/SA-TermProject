package com.example.carrental.Logic.ChainOfResponsability

import com.example.carrental.Logic.Mediator.Mediator
import com.example.carrental.database.model.User

class QuestionOne : QuestionHandler() {

    //check if question one matches
    override fun handleRequest(user: User, Q1: String, Q2: String, Q3: String) {
        if (user.Q1 == Q1){
            successor?.handleRequest(user, Q1, Q2, Q3)
        } else {
            Mediator.setPasswordFlag()
        }
    }
}