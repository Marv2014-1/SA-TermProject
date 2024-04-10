package com.example.carrental.Logic.Mediator

/**
 * This is the Mediator class and it handles logic between the UI elements and the database
 */

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.carrental.Logic.Builder.CarBuilder
import com.example.carrental.Logic.ChainOfResponsability.QuestionHandler
import com.example.carrental.Logic.ChainOfResponsability.QuestionOne
import com.example.carrental.Logic.ChainOfResponsability.QuestionThree
import com.example.carrental.Logic.ChainOfResponsability.QuestionTwo
import com.example.carrental.Logic.Observer.DatabaseSubject
import com.example.carrental.Logic.Observer.UserObserver
import com.example.carrental.Logic.proxy.PaymentProxy
import com.example.carrental.Logic.proxy.PaymentServiceConcrete
import com.example.carrental.Logic.Singleton.Session
import com.example.carrental.UI.Adapters.NotificationAdapter
import com.example.carrental.UI.FilterCar
import com.example.carrental.UI.ForgotPassword
import com.example.carrental.UI.Garage
import com.example.carrental.UI.History
import com.example.carrental.UI.MainActivity
import com.example.carrental.UI.Menu
import com.example.carrental.UI.MessageListing
import com.example.carrental.UI.NewCar
import com.example.carrental.UI.NewPassword
import com.example.carrental.UI.NewUser
import com.example.carrental.UI.Notifications
import com.example.carrental.UI.People
import com.example.carrental.UI.UpdateCar
import com.example.carrental.database.CarTable
import com.example.carrental.database.MessageTable
import com.example.carrental.database.RentalTable
import com.example.carrental.database.ReviewTable
import com.example.carrental.database.model.User
import com.example.carrental.database.UserTable
import com.example.carrental.database.model.Car
import com.example.carrental.database.model.Message
import com.example.carrental.database.model.Rental
import com.example.carrental.database.model.Review

object Mediator {
    //indicates what view was previously used
    private var previousState : String = ""
    //indicates if the questions were answered wrong
    private var passwordFlag : Boolean = false
    private var filter : Car = Car()
    private val sender = DatabaseSubject()
    private val receiver = UserObserver()

    //this function handles the login button in Main and starts the session
    @RequiresApi(Build.VERSION_CODES.O)
    fun login(context : Context, username : String, password : String){
        val userTable = UserTable(context)
        val user = userTable.getByUsernamePassword(username, password)

        filter = Car()

        if (user == null){
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra("error", "Incorrect entry")
            context.startActivity(intent)
            return
        }

        //implement the Singleton pattern to manage the user's session securely.
        var session = Session.getInstance()
        session.startSession(user)

        //Check for updates from the observer and add notifications to the session
        sender.registerObserver(receiver)
        sender.searchDatabaseForUpdates(context, user.id!!)

        menu(context)
    }

    //Switch to the menu seen
    private fun menu(context: Context){
        val intent = Intent(context, Menu::class.java)
        context.startActivity(intent)

        previousState = "main"
    }

    //Retrieve the balance of the current user
    fun getUserBalance() : Long{
        val session = Session.getInstance()
        return session.getUser().balance!!
    }

    //Retrieve the name of the given user id
    fun getUserName(context: Context ,id : Long) : String{
        var userTable = UserTable(context)
        return userTable.getByID(id).username.toString()
    }

    //Get all the users
    fun getUsers(context: Context) : ArrayList<User>{
        val db = UserTable(context)
        return db.getALL()
    }

    //Get all users except for the one currently logged in
    fun getAllOtherUers(context: Context) : ArrayList<User>{
        val user = Session.getInstance().getUser()
        val db = UserTable(context)
        return db.getALLOthers(user.id!!)
    }

    //Get the car model of the given ID
    fun getCarModel(context: Context ,id : Long) : String{
        var carTable = CarTable(context)
        return carTable.getByID(id)?.model.toString()
    }

    //Get the score of the given user id
    fun getScore(context: Context, id : Long) : String?{
        val reviewTable = ReviewTable(context)
        val review =  reviewTable.getByID(id)

        if (review != null) {
            return review.score.toString()
        }

        return "null"
    }

    //this function handles the new user button in Main
    fun newUser(context : Context){
        val intent = Intent(context, NewUser::class.java)
        context.startActivity(intent)

        previousState = "main"
    }

    //this function handles the submit new user button in NewUser
    fun addNewUser(context: Context, username : String, password: String, Q1 : String, Q2 : String, Q3 : String, balance : Long){
        val user : User = User(username, password, Q1, Q2, Q3, balance)
        val userTable = UserTable(context)
        val code = userTable.insert(user)

        val intent = Intent(context, MainActivity::class.java)
        if (code?.toInt() == -1){
            intent.putExtra("error", "User insert failed!")
        } else {
            intent.putExtra("State", "User Added Successfully!")
        }
        context.startActivity(intent)
    }

    //this function handles the forgot password in Main
    fun forgotPassword(context : Context){
        val intent = Intent(context, ForgotPassword::class.java)
        context.startActivity(intent)

        previousState = "main"
    }

    //this function handles the submit button in ForgotPassword using chain of responsibility
    fun retrievePassword(context: Context, username: String, questionOne : String, questionTwo : String, questionThree : String){
        val userTable = UserTable(context)
        val user = userTable.getByUsername(username)

        val Q1 : QuestionHandler = QuestionOne()
        val Q2 : QuestionHandler = QuestionTwo()
        val Q3 : QuestionHandler = QuestionThree()

        Q1.setNext(Q2)
        Q2.setNext(Q3)

        if (user != null) {
            Q1.handleRequest(user, questionOne, questionTwo, questionThree)
        } else {
            passwordFlag = true
        }

        if (passwordFlag == false){
            val intent = Intent(context, NewPassword::class.java)
            intent.putExtra("username" , username)
            context.startActivity(intent)
        } else {
            passwordFlag = false
            val intent = Intent(context, ForgotPassword::class.java)
            intent.putExtra("error", "The answers/usernameMar were wrong")
            context.startActivity(intent)
        }
    }

    // indicates that the password was incorrect and warns the user
    fun setPasswordFlag(){
        passwordFlag = true
    }

    //this function handles the submit button in NewPassword
    fun newPassword(context : Context, username: String, password : String){
        val userTable = UserTable(context)
        val user : User = User(username, password)
        userTable.update(user)

        val intent = Intent(context, MainActivity::class.java)
        intent.putExtra("state" , "Password changed")
        context.startActivity(intent)
    }

    // display the cars in the user's garage and allow them to add/modify them
    fun garage(context: Context){
        val intent = Intent(context, Garage::class.java)
        context.startActivity(intent)
        previousState = "menu"
    }

    // return all the cars that the user owns for display in the garage
    fun getUserCars(context: Context): ArrayList<Car> {
        val carTable = CarTable(context)
        var session = Session.getInstance()
        val user = session.getUser()
        return carTable.getByOwner(user)
    }

    // return the cars of all users except for mine
    fun getOthersCars(context: Context) : ArrayList<Car> {
        val carTable = CarTable(context)
        var session = Session.getInstance()
        val user = session.getUser()
        return carTable.getOthers(user, filter)
    }

    // change to the garage view
    fun getCar(context: Context, id : Long) : Car{
        val carTable = CarTable(context)
        val car : Car = carTable.getByID(id)!!
        return car
    }

    // allow the user to initiate the creation of a new car
    fun newCar(context: Context){
        val intent = Intent(context, NewCar::class.java)
        context.startActivity(intent)
        previousState = "garage"
    }

    // add the new car to the database
    fun addNewCar(context: Context, model : String, year : String, mileage : Int, availability : String, location : String, price : Int){
        val builder = CarBuilder()
        builder.setModel(model)
        builder.setYear(year)
        builder.setMileage(mileage)
        builder.setAvailability(availability)
        builder.setLocation(location)
        builder.setPrice(price)

        val car = builder.getResult()
        var session = Session.getInstance()
        val currentUser = session.getUser()
        car.owner = currentUser.id!!

        val carTable = CarTable(context)
        carTable.insert(car)

        garage(context)
    }

    // delete a listed car
    fun deleteCar(context: Context, id : Long){
        val cartable = CarTable(context)
        cartable.deleteById(id)

        garage(context)
    }

    // edit a listed car
    fun editCar(context: Context, id : Long){
        val intent = Intent(context, UpdateCar::class.java)
        intent.putExtra("ID", id)
        context.startActivity(intent)
        previousState = "garage"
    }

    // update the listed car that is being edited
    fun updateCarContent(context: Context, id : Long,  model : String, year : String, mileage : Int, availability : String, location : String, price : Int){
        val builder = CarBuilder()
        builder.setId(id)
        builder.setModel(model)
        builder.setYear(year)
        builder.setMileage(mileage)
        builder.setAvailability(availability)
        builder.setLocation(location)
        builder.setPrice(price)

        val car = builder.getResult()
        var session = Session.getInstance()
        val currentUser = session.getUser()
        car.owner = currentUser.id!!

        val carTable = CarTable(context)
        carTable.update(car)

        garage(context)
    }

    private var moneyFlag = true

    // rent a car that is listed
    fun rentCar(context: Context, target : Long, car : Car){
        val paymentProxy : PaymentProxy = PaymentProxy(context, PaymentServiceConcrete(context))

        paymentProxy.processPayment(target, car)

        val intent = Intent(context, Menu::class.java)
        if (moneyFlag == false){
            moneyFlag = true
            intent.putExtra("error", "You don't have the money")
        }
        context.startActivity(intent)
    }

    // checks to see if the user has the funds
    fun hasMoney(boolean: Boolean){
        moneyFlag = boolean
    }

    // change to the history view
    fun history(context: Context){
        val intent = Intent(context, History::class.java)
        context.startActivity(intent)

        previousState = "menu"
    }

    // write to the history table once clicked
    fun setHistory(context: Context): ArrayList<Rental> {
        val session = Session.getInstance()
        val user = session.getUser()

        val rentalTable = RentalTable(context)
        return rentalTable.getAllMine(user.id!!)
    }

    // allow for users to search for people and review them
    fun people(context: Context){
        val intent = Intent(context, People::class.java)
        context.startActivity(intent)

        previousState = "menu"
    }

    // Change to the review view
    fun reviewPage(context: Context, target: Long){
        val intent = Intent(context, com.example.carrental.UI.Review::class.java)
        intent.putExtra("target", target.toString())
        context.startActivity(intent)
        previousState = "people"
    }

    // upload a review to a specific target and pass their score (5 max)
    fun review(context: Context, target: Long, score : Double){
        val session = Session.getInstance()
        val user = session.getUser()
        val db = ReviewTable(context)
        var review = Review()
        review.reviewer = user.id
        review.target = target
        review.score = score

        db.insert(review)

        people(context)
    }

    // switch to the car filter view
    fun filter(context: Context){
        val intent = Intent(context, FilterCar::class.java)
        context.startActivity(intent)

        previousState = "menu"
    }

    // set the current filter to the given values
    fun setFilter(context: Context, model : String, availability: String, location: String, low: Int, high: Int){
        filter.model = model
        filter.availability = availability
        filter.location = location
        if (low < high) {
            filter.low = low
            filter.high = high
        }

        menu(context)
    }

    // change to the message view
    fun message(context: Context , target: Long){
        val intent = Intent(context, MessageListing::class.java)
        intent.putExtra("Id" , target)
        context.startActivity(intent)

        previousState = "people"
    }

    // send a message to the user selected
    @RequiresApi(Build.VERSION_CODES.O)
    fun sendMessage(context: Context, target: Long, text : String){
        val messageTable = MessageTable(context)
        val session = Session.getInstance()
        val user = session.getUser()

        var message = Message(text, target, user.id!!)

        messageTable.insert(message)

        message(context, target)
    }

    // get the chat between two users
    @RequiresApi(Build.VERSION_CODES.O)
    fun getMessages(context: Context, targetId : Long) : ArrayList<Message>{
        val messageTable = MessageTable(context)
        val session = Session.getInstance()
        val user = session.getUser()
        val result = messageTable.getCurrentConvo(user.id!!, targetId)
        return result
    }

    // change to the notification view
    fun notifications(context: Context){
        val intent = Intent(context, Notifications::class.java)
        previousState = "menu"
        context.startActivity(intent)
    }

    // end user session
    fun resetSession(){
        val session = Session.getInstance()
        session.endSession()
    }

    //this function handles the revert button
    fun back(context : Context){

        if (this.previousState == "main"){
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
            sender.unregisterObserver(receiver)
        }else if (this.previousState == "menu"){
            val intent = Intent(context, Menu::class.java)
            context.startActivity(intent)
            this.previousState = "main"
        }else if (this.previousState == "garage"){
            this.previousState = "menu"
            garage(context)
        }else if (this.previousState == "people"){
            people(context)
        }
    }
}