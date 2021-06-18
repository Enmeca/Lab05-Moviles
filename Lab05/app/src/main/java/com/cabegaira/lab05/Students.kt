package com.cabegaira.lab05

import java.io.Serializable

class Students : Serializable {

    var id: Int = 0
    var name: String = ""
    var lastname: String = ""
    var age : Int = 0

    internal constructor(id:Int, name:String, lastname:String, age:Int){
        this.id = id
        this.name = name
        this.lastname = lastname
        this.age = age
    }
}