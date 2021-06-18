package com.cabegaira.lab05

import java.io.Serializable

class Courses : Serializable{

    var id:Int=0;
    var description:String="";
    var credits:Int=0;

    constructor(id: Int, description: String, credits: Int) {
        this.id = id
        this.description = description
        this.credits = credits
    }
}