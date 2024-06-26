package com.example.carrental.database.model

/**
 * this class models the review and it's variables as seen on the review table
 */

class Review {
    var id : Long? = null
    var reviewer : Long? = null
    var target : Long? = null
    var content : String? = null
    var score : Int? = null

    constructor(){
        id = -1
        reviewer = -1
        target = -1
        content = ""
        score = -1
    }

    constructor(id : Long, reviewer : Long, target : Long, content : String, score : Int){
        this.id = id
        this.reviewer = reviewer
        this.target = target
        this.content = content
        this.score = score
    }
}