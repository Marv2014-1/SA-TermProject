package com.example.carrental.Logic.Mediator

import android.content.Context
import android.content.Intent
import com.example.carrental.Logic.ChainOfResponsability.QuestionHandler
import com.example.carrental.Logic.ChainOfResponsability.QuestionOne
import com.example.carrental.Logic.ChainOfResponsability.QuestionThree
import com.example.carrental.Logic.ChainOfResponsability.QuestionTwo
import com.example.carrental.Logic.Singleton.Session
import com.example.carrental.UI.ForgotPassword
import com.example.carrental.UI.MainActivity
import com.example.carrental.UI.Menu
import com.example.carrental.UI.NewPassword
import com.example.carrental.UI.NewUser
import com.example.carrental.database.model.User
import com.example.carrental.database.UserTable

object Mediator {
    //indicates what view was previously used
    private var previousState : String = ""
    //indicates if the questions were answered wrong
    private var passwordFlag : Boolean = false

    //this function handles the login button in Main
    fun login(context : Context, username : String, password : String){
        val userTable = UserTable(context)
        val user = userTable.getByUsernamePassword(username, password)

        if (user == null){
            var intent = Intent(context, MainActivity::class.java)
            intent.putExtra("error", "Incorrect entry")
            context.startActivity(intent)
            return
        }

        //implement the Singleton pattern to manage the user's session securely.
        var session = Session.getInstance()
        session.startSession(user)

        var intent = Intent(context, Menu::class.java)
        context.startActivity(intent)

        previousState = "main"
    }

    //this function handles the new user button in Main
    fun newUser(context : Context){
        var intent = Intent(context, NewUser::class.java)
        context.startActivity(intent)

        previousState = "main"
    }

    //this function handles the submit new user button in NewUser
    fun addNewUser(context: Context, username : String, password: String, Q1 : String, Q2 : String, Q3 : String, balance : Long){
        val user : User = User(username, password, Q1, Q2, Q3, balance)
        val userTable = UserTable(context)
        val code = userTable.insert(user)

        var intent = Intent(context, MainActivity::class.java)
        if (code?.toInt() == -1){
            intent.putExtra("error", "User insert failed!")
        } else {
            intent.putExtra("State", "User Added Successfully!")
        }
        context.startActivity(intent)
    }

    //this function handles the forgot password in Main
    fun forgotPassword(context : Context){
        var intent = Intent(context, ForgotPassword::class.java)
        context.startActivity(intent)

        previousState = "main"
    }

    //this function handles the submit button in ForgotPassword using chain of responsibility
    fun retrievePassword(context: Context, username: String, questionOne : String, questionTwo : String, questionThree : String){
        var userTable = UserTable(context)
        var user = userTable.getByUsername(username)

        var Q1 : QuestionHandler = QuestionOne()
        var Q2 : QuestionHandler = QuestionTwo()
        var Q3 : QuestionHandler = QuestionThree()

        Q1.setNext(Q2)
        Q2.setNext(Q3)

        if (user != null) {
            Q1.handleRequest(user, questionOne, questionTwo, questionThree)
        } else {
            passwordFlag = true
        }

        if (passwordFlag == false){
            var intent = Intent(context, NewPassword::class.java)
            intent.putExtra("username" , username)
            context.startActivity(intent)
        } else {
            passwordFlag = false
            var intent = Intent(context, ForgotPassword::class.java)
            intent.putExtra("error", "The answers/usernameMar were wrong")
            context.startActivity(intent)
        }
    }

    fun setPasswordFlag(){
        passwordFlag = true
    }

    //this function handles the submit button in NewPassword
    fun newPassword(context : Context, username: String, password : String){
        var userTable = UserTable(context)
        var user : User = User(username, password)
        userTable.update(user)

        var intent = Intent(context, MainActivity::class.java)
        intent.putExtra("state" , "Password changed")
        context.startActivity(intent)
    }

    //this function handles the revert button
    fun back(context : Context){

        if (previousState == "main"){
            var intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }
}