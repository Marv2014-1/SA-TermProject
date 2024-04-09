package com.example.carrental.database.model

/**
 * this class models the review and it's variables as seen on the review table
 */

class Review {
    var id : Long? = null
    var reviewer : Long? = null
    var target : Long? = null
    var content : String? = null
    var score : Double? = null
    var seen : Boolean = false

    constructor(){
        id = -1
        reviewer = -1
        target = -1
        content = ""
        score = -1.0
    }

    constructor(id : Long, reviewer : Long, target : Long, content : String, score : Double){
        this.id = id
        this.reviewer = reviewer
        this.target = target
        this.content = content
        this.score = score
    }

    fun setScore(score: Double){
        var points = score
        if (points > 5){
            points = 5.0
        }

        if (points < 0){
            points = 0.0
        }

        this.score = points
    }
}