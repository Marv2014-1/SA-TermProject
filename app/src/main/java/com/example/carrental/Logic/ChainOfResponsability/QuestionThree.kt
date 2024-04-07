package com.example.carrental.Logic.ChainOfResponsability

import com.example.carrental.Logic.Mediator.Mediator
import com.example.carrental.database.model.User

class QuestionThree : QuestionHandler()  {
    override fun handleRequest(user: User, Q1: String, Q2: String, Q3: String) {
        if (user.Q3 == Q3){
            return
        } else {
            Mediator.setPasswordFlag()
        }
    }
}